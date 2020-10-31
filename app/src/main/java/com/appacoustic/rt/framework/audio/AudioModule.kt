package com.appacoustic.rt.framework.audio

import com.appacoustic.rt.domain.calculator.ReverbTimeCalculator
import com.appacoustic.rt.framework.audio.player.Player
import com.appacoustic.rt.framework.audio.recorder.Recorder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val audioModule = module {
    single {
        Player(
            context = androidContext(),
            analyticsTrackerComponent = get(),
            errorTrackerComponent = get()
        )
    }
    single {
        Recorder(
            context = androidContext(),
            reverbTimeCalculator = ReverbTimeCalculator(
                analyticsTrackerComponent = get()
            ),
            userSession = get(),
            player = get(),
            analyticsTrackerComponent = get(),
            errorTrackerComponent = get()
        )
    }
}
