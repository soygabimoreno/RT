package com.appacoustic.rt.framework.audio.recorder

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import arrow.core.Either
import arrow.core.left
import com.appacoustic.rt.R
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.data.analytics.error.ErrorTrackerComponent
import com.appacoustic.rt.domain.Measure
import com.appacoustic.rt.domain.UserSession
import com.appacoustic.rt.domain.calculator.ReverbTimeCalculator
import com.appacoustic.rt.framework.KLog
import com.appacoustic.rt.framework.audio.player.Player
import com.appacoustic.rt.framework.audio.recorder.analytics.RecorderEvents
import com.appacoustic.rt.framework.extension.getSizeInMB
import com.appacoustic.rt.framework.extension.isInitialized
import com.appacoustic.rt.framework.extension.isRecording
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class Recorder(
    private val context: Context,
    private val reverbTimeCalculator: ReverbTimeCalculator,
    private val userSession: UserSession,
    private val player: Player,
    private val analyticsTrackerComponent: AnalyticsTrackerComponent,
    private val errorTrackerComponent: ErrorTrackerComponent
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
    }

    private var tempPath = getTempPath()
    private var path = getPath()

    private lateinit var audioRecord: AudioRecord
    private var bufferSize = 0
    private var totalAudioLength = 0L

    private var xBytes: ByteArray = byteArrayOf()
//    private var xBytes = context.resources.openRawResource(R.raw.clap).readBytes() // COMMENT: For checks

    private var recording = false

    fun start() {
        audioRecord = buildAudioRecord()
        if (!audioRecord.isInitialized()) {
            errorTrackerComponent.trackError(Exception("AudioRecord is not initialized"))
            return
        }

        bufferSize = audioRecord.bufferSizeInFrames
        KLog.d("bufferSize: $bufferSize")

        audioRecord.startRecording()
        if (!audioRecord.isRecording()) {
            errorTrackerComponent.trackError(Exception("AudioRecord is not recording"))
            return
        }
        recording = true

        CoroutineScope(Dispatchers.IO).launch {
            writeRawTempFile()
        }


        if (userSession.isTestSignalEnabled()) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(100)
                player.start(R.raw.white_noise_100ms)
            }
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

    suspend fun stop(): Either<Throwable, List<Measure>> {
        try {
            recording = false
            audioRecord.stop()
            audioRecord.release()

            writeWavFile()
            buildByteArrayFromTempFile()
            deleteFile(tempPath)
            return if (xBytes.isNotEmpty()) {
                calculateReverbTime()
            } else {
                val error = Exception("xBytes is empty after stop recording")
                errorTrackerComponent.trackError(error)
                error.left()
            }
        } catch (e: Exception) {
            errorTrackerComponent.trackError(e)
            return e.left()
        }
    }

    fun calculateReverbTime(): Either<Throwable, List<Measure>> =
        reverbTimeCalculator(
            xBytes,
            SAMPLE_RATE
        )

    fun getXBytes() = xBytes

    private suspend fun writeRawTempFile() = coroutineScope {
        launch {
            try {
                analyticsTrackerComponent.trackEvent(RecorderEvents.DataTempPath(tempPath))
                analyticsTrackerComponent.trackEvent(RecorderEvents.DataBufferSize(bufferSize))
                val fos = FileOutputStream(tempPath)
                analyticsTrackerComponent.trackEvent(RecorderEvents.DataFileOutputStreamCreated)
                val data = ByteArray(bufferSize)
                while (recording) {
                    val read = audioRecord.read(
                        data,
                        0,
                        bufferSize
                    )
                    if (read != AudioRecord.ERROR_INVALID_OPERATION) {
                        fos.write(data)
                    }
                }
                analyticsTrackerComponent.trackEvent(RecorderEvents.DataAudioRecorded)
                fos.close()
                analyticsTrackerComponent.trackEvent(RecorderEvents.DataFileOutputStreamClosed)
            } catch (e: Exception) {
                errorTrackerComponent.trackError(e)
                e.printStackTrace()
            }
        }
    }

    private fun getTempPath(): String {
        val filePath = context.filesDir
        val file = File(
            filePath,
            FOLDER_RECORDINGS
        )

        if (!file.exists()) {
            file.mkdirs()
        }

        val tempFilename = "record_temp.raw"
        val tempFile = File(
            filePath,
            tempFilename
        )
        if (tempFile.exists()) {
            tempFile.delete()
        }

        return file.absolutePath + "/" + tempFilename
    }

    private fun getPath(): String {
        val filePath = context.filesDir
        val file = File(
            filePath,
            FOLDER_RECORDINGS
        )

        if (!file.exists()) {
            file.mkdirs()
        }

        return file.absolutePath + "/" + System.currentTimeMillis() + EXTENSION_WAV
    }

    private suspend fun writeWavFile() = coroutineScope {
        launch {
            try {
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
            } catch (e: Exception) {
                errorTrackerComponent.trackError(e)
                e.printStackTrace()
            }

//            val mediaPlayer = MediaPlayer.create(context, Uri.parse(path))
//            mediaPlayer.start()
        }
    }

    private suspend fun buildByteArrayFromTempFile() = coroutineScope {
        launch {
            try {
                val fis = FileInputStream(tempPath)
                val baos = ByteArrayOutputStream()
                val data = ByteArray(bufferSize)

                while (fis.read(data) != -1) {
                    baos.write(data)
                }

                KLog.d("baos size: ${baos.getSizeInMB()} MB")
                xBytes = ByteArray(baos.size())
                xBytes = baos.toByteArray()
                fis.close()
                baos.close()
            } catch (e: Exception) {
                errorTrackerComponent.trackError(e)
                e.printStackTrace()
            }
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
}
