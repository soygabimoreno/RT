package com.appacoustic.rt.framework.audio.player

import android.content.Context
import android.media.MediaPlayer
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.data.analytics.error.ErrorTrackerComponent
import com.appacoustic.rt.framework.audio.player.analytics.PlayerEvents

class Player(
    private val context: Context,
    private val analyticsTrackerComponent: AnalyticsTrackerComponent,
    private val errorTrackerComponent: ErrorTrackerComponent
) {

    fun start(rawResId: Int) {
        analyticsTrackerComponent.trackEvent(PlayerEvents.PlayerStart)
        val mediaPlayer = MediaPlayer.create(
            context,
            rawResId
        )
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
        }
    }
}
