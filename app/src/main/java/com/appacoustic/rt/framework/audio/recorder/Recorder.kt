package com.appacoustic.rt.framework.audio.recorder

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.appacoustic.rt.framework.KLog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class Recorder(
    private val context: Context,
    private val audioRecord: AudioRecord
) {

    companion object {
        const val AUDIO_SOURCE = MediaRecorder.AudioSource.MIC
        const val SAMPLE_RATE = 44100
        const val CHANNELS = AudioFormat.CHANNEL_IN_MONO
        const val AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT
        private const val BITS_PER_SAMPLE = 16

        private const val FILE_EXTENSION = ".wav"
        private const val FOLDER_MAIN = "RT" // TODO: If finally we use it internally, it does not make sense to call this way: "Records" or similar
        private const val FOLDER_CSV = "$FOLDER_MAIN/CSV"
        private const val FILENAME_TEMP = "record_temp.raw"
    }

    private var totalAudioLength = 0
    private lateinit var xBytes: ByteArray

    private var recording = false

    fun start() {
        KLog.d("audioRecord: $audioRecord")
        audioRecord.startRecording()
        recording = true
        GlobalScope.launch {
            writeWavFile()
        }
    }

    fun stop() {
        recording = false
    }

    private suspend fun writeWavFile() = coroutineScope {
        launch {
            val tempFilename = getTempFilename()
            KLog.d("tempFilename: $tempFilename")

            val fos = FileOutputStream(tempFilename)
            val bufferSize = audioRecord.bufferSizeInFrames
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

    private fun getTempFilename(): String {
//        val filePath = Environment.getExternalStorageDirectory().path // ERASE
        val filePath = context.filesDir
        val file = File(filePath, FOLDER_MAIN)

        if (!file.exists()) {
            file.mkdirs()
        }

        val tempFile = File(filePath, FILENAME_TEMP)
        if (tempFile.exists()) {
            tempFile.delete()
        }

        return file.absolutePath + "/" + FILENAME_TEMP
    }
}
