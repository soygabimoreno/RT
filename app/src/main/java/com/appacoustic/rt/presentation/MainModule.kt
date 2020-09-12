package com.appacoustic.rt.presentation

import com.appacoustic.rt.domain.PermissionRequester
import com.appacoustic.rt.domain.RecordAudioPermissionChecker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {
    single { PermissionRequester(androidContext()) }
    scope(named<MainActivity>()) {
        viewModel {
            MainViewModel(
                RecordAudioPermissionChecker(
                    permissionRequester = get()
                )
            )
        }
    }
}
