package com.appacoustic.rt.presentation.main

import com.appacoustic.rt.data.DefaultUserSession
import com.appacoustic.rt.domain.UserSession
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {
    single<UserSession> { DefaultUserSession(persistentRepository = get()) }
    scope(named<MainActivity>()) {
        viewModel {
            MainViewModel(
                userSession = get(),
                analyticsTrackerComponent = get()
            )
        }
    }
}
