package com.appacoustic.rt.presentation.signal.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val SCREEN_SIGNAL = "SCREEN_SIGNAL"
private const val CLICK_ENABLE_FILTER = "CLICK_ENABLE_FILTER"
private const val CLICK_DISABLE_FILTER = "CLICK_DISABLE_FILTER"
private const val CLICK_SPINNER_FREQUENCY = "CLICK_SPINNER_FREQUENCY"
private const val CLICK_SPINNER_ORDER = "CLICK_SPINNER_ORDER"

private const val FREQUENCY = "FREQUENCY"
private const val ORDER = "ORDER"

sealed class SignalEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenSignal : SignalEvents(SCREEN_SIGNAL)
    object ClickEnableFilter : SignalEvents(CLICK_ENABLE_FILTER)
    object ClickDisableFilter : SignalEvents(CLICK_DISABLE_FILTER)

    class ClickSpinnerFrequency(frequency: String) : SignalEvents(
        CLICK_SPINNER_FREQUENCY,
        mapOf(
            FREQUENCY to frequency
        )
    )

    class ClickSpinnerOrder(order: String) : SignalEvents(
        CLICK_SPINNER_ORDER,
        mapOf(
            ORDER to order
        )
    )
}
