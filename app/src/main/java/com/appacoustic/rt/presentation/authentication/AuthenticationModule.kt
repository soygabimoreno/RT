package com.appacoustic.rt.presentation.authentication

import com.appacoustic.rt.data.DefaultUserSession
import com.appacoustic.rt.domain.UserSession
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authenticationModule = module {
    single<UserSession> { DefaultUserSession(persistentRepository = get()) }
    scope(named<AuthenticationActivity>()) {
        viewModel {
            AuthenticationViewModel(
                userSession = get(),
                analyticsTrackerComponent = get()
            )
        }
    }
}
