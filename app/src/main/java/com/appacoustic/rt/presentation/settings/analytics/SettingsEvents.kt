package com.appacoustic.rt.presentation.settings.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val SCREEN_SETTINGS = "SCREEN_SETTINGS"
private const val CLICK_ENABLE_TEST_SIGNAL = "CLICK_ENABLE_TEST_SIGNAL"
private const val CLICK_DISABLE_TEST_SIGNAL = "CLICK_DISABLE_FILTER"

sealed class SettingsEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenSettings : SettingsEvents(SCREEN_SETTINGS)
    object ClickEnableTestSignal : SettingsEvents(CLICK_ENABLE_TEST_SIGNAL)
    object ClickDisableTestSignal : SettingsEvents(CLICK_DISABLE_TEST_SIGNAL)
}
