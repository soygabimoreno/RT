package com.appacoustic.rt.framework.audio.player.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val PLAYER_START = "PLAYER_START"

sealed class PlayerEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object PlayerStart : PlayerEvents(PLAYER_START)
}
