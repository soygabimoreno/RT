package com.appacoustic.rt

import com.appacoustic.rt.data.analytics.error.errorTrackerModule
import com.appacoustic.rt.framework.audio.audioModule
import com.appacoustic.rt.presentation.main.mainModule
import com.appacoustic.rt.presentation.measure.measureModule
import com.appacoustic.rt.presentation.permission.permissionModule
import com.appacoustic.rt.presentation.signals.signalModule

val serviceLocator = listOf(
    errorTrackerModule,

    mainModule,
    permissionModule,
    measureModule,
    signalModule,

    audioModule
)
