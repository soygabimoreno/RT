package com.appacoustic.rt.presentation.measure.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val SCREEN_MEASURE = "SCREEN_MEASURE"
private const val CLICK_START = "CLICK_START"

sealed class MeasureEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenMeasure : MeasureEvents(SCREEN_MEASURE)
    object ClickStart : MeasureEvents(CLICK_START)
}
