package com.appacoustic.rt.presentation.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {
    scope(named<MainActivity>()) {
        viewModel {
            MainViewModel(
                userSession = get(),
                analyticsTrackerComponent = get()
            )
        }
    }
}
