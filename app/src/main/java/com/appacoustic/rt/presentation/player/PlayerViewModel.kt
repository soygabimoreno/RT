package com.appacoustic.rt.presentation.player

import androidx.annotation.RawRes
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.framework.audio.player.Player
import com.appacoustic.rt.framework.base.viewmodel.StatelessBaseViewModel
import com.appacoustic.rt.presentation.player.analytics.PlayerEvents

class PlayerViewModel(
    private val player: Player,
    private val analyticsTrackerComponent: AnalyticsTrackerComponent
) : StatelessBaseViewModel<
    PlayerViewModel.ViewEvents>() {

    init {
        analyticsTrackerComponent.trackEvent(PlayerEvents.ScreenPlayer)
    }

    fun handlePlayButtonClicked(
        filename: String,
        @RawRes rawResId: Int
    ) {
        player.start(rawResId)
        analyticsTrackerComponent.trackEvent(PlayerEvents.ClickPlayTestSignal(filename))
    }

    sealed class ViewEvents {
        data class Foo(val foo: Boolean) : ViewEvents()
    }
}
