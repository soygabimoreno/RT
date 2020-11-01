package com.appacoustic.rt.presentation.player.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val SCREEN_PLAYER = "SCREEN_PLAYER"
private const val CLICK_PLAY_TEST_SIGNAL = "CLICK_PLAY_TEST_SIGNAL"

private const val FINENAME = "FINENAME"

sealed class PlayerEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenPlayer : PlayerEvents(SCREEN_PLAYER)

    class ClickPlayTestSignal(
        filename: String
    ) : PlayerEvents(
        CLICK_PLAY_TEST_SIGNAL,
        mapOf(
            FINENAME to filename
        )
    )
}
