package com.appacoustic.rt.presentation.player

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val playerModule = module {
    scope(named<PlayerFragment>()) {
        viewModel {
            PlayerViewModel(
                player = get(),
                analyticsTrackerComponent = get()
            )
        }
    }
}
