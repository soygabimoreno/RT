package com.appacoustic.rt.framework.audio

import android.media.AudioRecord
import com.appacoustic.rt.framework.audio.recorder.Recorder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val audioModule = module {
    single {
        AudioRecord(
            Recorder.AUDIO_SOURCE,
            Recorder.SAMPLE_RATE,
            Recorder.CHANNELS,
            Recorder.AUDIO_ENCODING,
            AudioRecord.getMinBufferSize(
                Recorder.SAMPLE_RATE,
                Recorder.CHANNELS,
                Recorder.AUDIO_ENCODING
            )
        )
    }
    single {
        Recorder(
            context = androidContext(),
            audioRecord = get()
        )
    }
}
