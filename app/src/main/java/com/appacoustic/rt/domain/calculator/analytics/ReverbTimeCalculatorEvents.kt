package com.appacoustic.rt.domain.calculator.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val DATA_BYTE_ARRAY_SIZE = "DATA_BYTE_ARRAY_SIZE"

private const val SIZE_IN_MB = "SIZE_IN_MB"

sealed class ReverbTimeCalculatorEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    class DataByteArraySize(sizeInMB: Float) : ReverbTimeCalculatorEvents(
        DATA_BYTE_ARRAY_SIZE,
        mapOf(
            SIZE_IN_MB to sizeInMB
        )
    )
}
