package com.appacoustic.rt.presentation.measure

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val measureModule = module {
    scope(named<MeasureFragment>()) {
        viewModel {
            MeasureViewModel(
                recordAudioPermissionChecker = get(),
                recorder = get(),
                userSession = get(),
                analyticsTrackerComponent = get(),
                errorTrackerComponent = get()
            )
        }
    }
}
