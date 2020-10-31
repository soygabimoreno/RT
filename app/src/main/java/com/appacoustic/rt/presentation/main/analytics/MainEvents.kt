package com.appacoustic.rt.presentation.main.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val SCREEN_MAIN = "SCREEN_MAIN"
private const val NAVIGATE_TO_MEASURE = "NAVIGATE_TO_MEASURE"
private const val CLICK_SHARE = "CLICK_SHARE"
private const val CLICK_EMAIL = "CLICK_EMAIL"
private const val CLICK_RATE = "CLICK_RATE"
private const val CLICK_INFO = "CLICK_INFO"
private const val CLICK_MEASURE = "CLICK_MEASURE"
private const val CLICK_SIGNAL = "CLICK_SIGNAL"
private const val CLICK_SETTINGS = "CLICK_SETTINGS"

sealed class MainEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenMain : MainEvents(SCREEN_MAIN)
    object NavigateToMeasure : MainEvents(NAVIGATE_TO_MEASURE)
    object ClickShare : MainEvents(CLICK_SHARE)
    object ClickEmail : MainEvents(CLICK_EMAIL)
    object ClickRate : MainEvents(CLICK_RATE)
    object ClickInfo : MainEvents(CLICK_INFO)
    object ClickMeasure : MainEvents(CLICK_MEASURE)
    object ClickSignal : MainEvents(CLICK_SIGNAL)
    object ClickSettings : MainEvents(CLICK_SETTINGS)
}
