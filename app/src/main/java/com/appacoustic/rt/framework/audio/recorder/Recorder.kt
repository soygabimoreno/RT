package com.appacoustic.rt.framework.audio.recorder

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.appacoustic.rt.framework.KLog

class Recorder(
    private val audioRecord: AudioRecord
) {

    companion object {
        const val AUDIO_SOURCE = MediaRecorder.AudioSource.MIC
        const val SAMPLE_RATE = 44100
        const val CHANNELS = AudioFormat.CHANNEL_IN_MONO
        const val AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT
        private const val BITS_PER_SAMPLE = 16

        private const val FILE_EXTENSION = ".wav"
        private const val FOLDER_MAIN = "RT"
        private const val FOLDER_CSV = "$FOLDER_MAIN/CSV"
        private const val FILENAME_TEMP = "record_temp.raw"
    }

    fun foo() {
        KLog.d("audioRecord: $audioRecord")
    }
}
