package com.appacoustic.rt.framework.audio.recorder

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder2
import com.appacoustic.rt.framework.KLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class Recorder(
    private val context: Context
) {

    companion object {
        const val AUDIO_SOURCE = MediaRecorder.AudioSource.MIC
        const val SAMPLE_RATE = 44100
        const val CHANNELS = AudioFormat.CHANNEL_IN_MONO
        const val N_CHANNELS = 1
        const val AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT
        private const val BITS_PER_SAMPLE = 16
        private const val BYTE_RATE = BITS_PER_SAMPLE * SAMPLE_RATE * N_CHANNELS / 8

        private const val EXTENSION_WAV = ".wav"
        private const val FOLDER_RECORDINGS = "RECORDINGS"
        private const val FOLDER_CSV = "CSV"
    }

    private var tempPath = getTempPath()
    private var path = getPath()

    private lateinit var audioRecord: AudioRecord
    private var bufferSize = 0
    private var totalAudioLength = 0L

    private lateinit var xBytes: ByteArray

    private var recording = false

    fun start() {
        audioRecord = buildAudioRecord()
        bufferSize = audioRecord.bufferSizeInFrames
        KLog.d("audioRecord: $audioRecord")
        audioRecord.startRecording()
        recording = true
        GlobalScope.launch {
            writeRawTempFile()
        }
    }

    private fun buildAudioRecord() = AudioRecord(
        AUDIO_SOURCE,
        SAMPLE_RATE,
        CHANNELS,
        AUDIO_ENCODING,
        AudioRecord.getMinBufferSize(
            SAMPLE_RATE,
            CHANNELS,
            AUDIO_ENCODING
        )
    )

    fun stop() {
        recording = false
        audioRecord.stop()
        audioRecord.release()

        GlobalScope.launch {
            writeWavFile()
            buildByteArrayFromTempFile()
            deleteFile(tempPath)
            calculateReverbTime()
        }
    }

    private suspend fun writeRawTempFile() = coroutineScope {
        launch {
            KLog.d("tempPath: $tempPath")

            val fos = FileOutputStream(tempPath)
            val data = ByteArray(bufferSize)
            while (recording) {
                val read = audioRecord.read(data, 0, bufferSize)
                if (read != AudioRecord.ERROR_INVALID_OPERATION) {
                    fos.write(data)
                }
            }
            fos.close()
        }
    }

    private fun getTempPath(): String {
        val filePath = context.filesDir
        val file = File(filePath, FOLDER_RECORDINGS)

        if (!file.exists()) {
            file.mkdirs()
        }

        val tempFilename = "record_temp.raw"
        val tempFile = File(filePath, tempFilename)
        if (tempFile.exists()) {
            tempFile.delete()
        }

        return file.absolutePath + "/" + tempFilename
    }

    private fun getPath(): String {
        val filePath = context.filesDir
        val file = File(filePath, FOLDER_RECORDINGS)

        if (!file.exists()) {
            file.mkdirs()
        }

        return file.absolutePath + "/" + System.currentTimeMillis() + EXTENSION_WAV
    }

    private suspend fun writeWavFile() = coroutineScope {
        launch {
            val fis = FileInputStream(tempPath)
            val fos = FileOutputStream(path)

            val data = ByteArray(bufferSize)
            totalAudioLength = fis.channel.size()
            val totalDataLength = totalAudioLength + 36
            writeWavHeader(
                fos = fos,
                totalAudioLength = totalAudioLength,
                totalDataLength = totalDataLength,
                sampleRate = SAMPLE_RATE,
                nChannels = N_CHANNELS,
                byteRate = BYTE_RATE,
                bitPerSample = BITS_PER_SAMPLE
            )
            while (fis.read(data) != -1) {
                fos.write(data)
            }
            fis.close()
            fos.close()

//            val mediaPlayer = MediaPlayer.create(context, Uri.parse(path))
//            mediaPlayer.start()
        }
    }

    private suspend fun buildByteArrayFromTempFile() = coroutineScope {
        launch {
            val fis = FileInputStream(tempPath)
            val baos = ByteArrayOutputStream()
            val data = ByteArray(bufferSize)

            while (fis.read(data) != -1) {
                baos.write(data)
            }

            xBytes = ByteArray(baos.size())
            xBytes = baos.toByteArray()

            fis.close()
            baos.close()
        }
    }

    private fun deleteFile(path: String) {
        val file = File(path)
        if (file.delete()) {
            KLog.d("File '$path' has been deleted successfully.")
        } else {
            KLog.d("Failure trying to delete the file '$path'.")
        }
    }

    private fun calculateReverbTime() {
        // ERASE: Not required anymore if we don't start to measure after click (and finish before pressing stop button)
//        xBytes = xBytes.muteStartAndEnd(0.25, SAMPLE_RATE)

        var x = xBytes.toDoubleSamples()
        x = x.windowingSignal(300, 100)
        x = x.toDivisibleBy32()
        x = x.normalize()

        var y125 = x.filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_125)
        var y250 = x.filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_250)
        var y500 = x.filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_500)
        var y1000 = x.filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_1000)
        var y2000 = x.filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_2000)
        var y4000 = x.filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_4000)

        y125 = y125.schroederIntegral()
        y250 = y250.schroederIntegral()
        y500 = y500.schroederIntegral()
        y1000 = y1000.schroederIntegral()
        y2000 = y2000.schroederIntegral()
        y4000 = y4000.schroederIntegral()

        y125 = y125.normalizeAndLinearToLogarithmic()
        y250 = y250.normalizeAndLinearToLogarithmic()
        y500 = y500.normalizeAndLinearToLogarithmic()
        y1000 = y1000.normalizeAndLinearToLogarithmic()
        y2000 = y2000.normalizeAndLinearToLogarithmic()
        y4000 = y4000.normalizeAndLinearToLogarithmic()

        val dBStart = ReverbTimeMethod.EDT.dBStart
        val dBEnd = ReverbTimeMethod.EDT.dBEnd
        val positionDbStart125 = y125.findPositionByAmplitude(dBStart)
        val positionDbEnd125 = y125.findPositionByAmplitude(dBEnd)
        val positionDbStart250 = y250.findPositionByAmplitude(dBStart)
        val positionDbEnd250 = y250.findPositionByAmplitude(dBEnd)
        val positionDbStart500 = y500.findPositionByAmplitude(dBStart)
        val positionDbEnd500 = y500.findPositionByAmplitude(dBEnd)
        val positionDbStart1000 = y1000.findPositionByAmplitude(dBStart)
        val positionDbEnd1000 = y1000.findPositionByAmplitude(dBEnd)
        val positionDbStart2000 = y2000.findPositionByAmplitude(dBStart)
        val positionDbEnd2000 = y2000.findPositionByAmplitude(dBEnd)
        val positionDbStart4000 = y4000.findPositionByAmplitude(dBStart)
        val positionDbEnd4000 = y4000.findPositionByAmplitude(dBEnd)

        val reverbTime125 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd125 - positionDbStart125).toDouble() / SAMPLE_RATE
        val reverbTime250 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd250 - positionDbStart250).toDouble() / SAMPLE_RATE
        val reverbTime500 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd500 - positionDbStart500).toDouble() / SAMPLE_RATE
        val reverbTime1000 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd1000 - positionDbStart1000).toDouble() / SAMPLE_RATE
        val reverbTime2000 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd2000 - positionDbStart2000).toDouble() / SAMPLE_RATE
        val reverbTime4000 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd4000 - positionDbStart4000).toDouble() / SAMPLE_RATE
    }
}
