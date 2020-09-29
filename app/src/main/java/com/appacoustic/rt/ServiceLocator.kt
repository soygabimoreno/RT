package com.appacoustic.rt

import com.appacoustic.rt.framework.audio.audioModule
import com.appacoustic.rt.presentation.main.mainModule
import com.appacoustic.rt.presentation.measure.measureModule
import com.appacoustic.rt.presentation.permission.permissionModule

val serviceLocator = listOf(
    mainModule,
    permissionModule,
    measureModule,

    audioModule
)
