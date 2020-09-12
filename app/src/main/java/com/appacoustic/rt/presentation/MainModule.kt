package com.appacoustic.rt.presentation

import com.appacoustic.rt.domain.CheckRecordAudioPermissionUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainModule = module {
    scope(named<MainActivity>()) {
        viewModel {
            MainViewModel(
                CheckRecordAudioPermissionUseCase()
            )
        }
    }
}
