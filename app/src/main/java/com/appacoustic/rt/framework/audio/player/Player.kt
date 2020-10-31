package com.appacoustic.rt.framework.audio.player

import android.content.Context
import android.media.MediaPlayer
import com.appacoustic.rt.R
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.data.analytics.error.ErrorTrackerComponent
import com.appacoustic.rt.framework.audio.player.analytics.PlayerEvents

class Player(
    context: Context,
    private val analyticsTrackerComponent: AnalyticsTrackerComponent,
    private val errorTrackerComponent: ErrorTrackerComponent
) {

    private val mediaPlayer = MediaPlayer.create(
        context,
        R.raw.white_noise_100ms
    )

    fun start() {
        analyticsTrackerComponent.trackEvent(PlayerEvents.PlayerStart)
        mediaPlayer.start()
    }
}
