package com.appacoustic.rt

import com.appacoustic.rt.data.analytics.analyticsTrackerModule
import com.appacoustic.rt.data.analytics.error.errorTrackerModule
import com.appacoustic.rt.data.persistent.db.persistentModule
import com.appacoustic.rt.framework.audio.audioModule
import com.appacoustic.rt.framework.showinappreview.showInAppReviewModule
import com.appacoustic.rt.presentation.main.mainModule
import com.appacoustic.rt.presentation.measure.measureModule
import com.appacoustic.rt.presentation.permission.permissionModule
import com.appacoustic.rt.presentation.player.playerModule
import com.appacoustic.rt.presentation.settings.settingsModule
import com.appacoustic.rt.presentation.signal.signalModule

val serviceLocator = listOf(
    analyticsTrackerModule,
    errorTrackerModule,

    persistentModule,

    showInAppReviewModule,

    mainModule,
    permissionModule,
    measureModule,
    signalModule,
    playerModule,
    settingsModule,

    audioModule
)
