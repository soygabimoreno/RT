package com.appacoustic.rt.presentation.authentication.register

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val registerModule = module {
    scope(named<RegisterFragment>()) {
        viewModel {
            RegisterViewModel(
                userSession = get(),
                analyticsTrackerComponent = get(),
                errorTrackerComponent = get()
            )
        }
    }
}
