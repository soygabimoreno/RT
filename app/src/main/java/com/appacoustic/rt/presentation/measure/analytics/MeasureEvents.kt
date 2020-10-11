package com.appacoustic.rt.presentation.measure.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val SCREEN_MEASURE = "SCREEN_MEASURE"
private const val CLICK_START = "CLICK_START"
private const val BUTTON_STATE = "BUTTON_STATE"
private const val DATA_MEASURES = "DATA_MEASURES"

private const val STATE = "STATE"
private const val MEASURES = "MEASURES"

sealed class MeasureEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenMeasure : MeasureEvents(SCREEN_MEASURE)

    object ClickStart : MeasureEvents(CLICK_START)

    class ButtonState(
        state: String,
    ) : MeasureEvents(
        BUTTON_STATE,
        mapOf(
            STATE to state
        )
    )

    class DataMeasures(
        measures: String,
    ) : MeasureEvents(
        DATA_MEASURES,
        mapOf(
            MEASURES to measures
        )
    )
}
