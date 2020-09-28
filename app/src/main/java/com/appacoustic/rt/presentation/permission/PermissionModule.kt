package com.appacoustic.rt.presentation.permission

import com.appacoustic.rt.domain.RecordAudioPermissionChecker
import com.appacoustic.rt.domainRecordAudioPermissionChecker.PermissionRequester
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val permissionModule = module {
    single { PermissionRequester(androidContext()) }
    single {
        RecordAudioPermissionChecker(
            permissionRequester = get()
        )
    }
    scope(named<PermissionFragment>()) {
        viewModel {
            PermissionViewModel(
                recordAudioPermissionChecker = get(),
                userSession = get()
            )
        }
    }
}
