package com.appacoustic.rt.presentation.signal

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val signalModule = module {
    scope(named<SignalFragment>()) {
        viewModel {
            SignalViewModel(
                recorder = get(),
                analyticsTrackerComponent = get()
            )
        }
    }
}
