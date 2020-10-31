package com.appacoustic.rt.presentation.settings

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val settingsModule = module {
    scope(named<SettingsFragment>()) {
        viewModel {
            SettingsViewModel(
                analyticsTrackerComponent = get()
            )
        }
    }
}
