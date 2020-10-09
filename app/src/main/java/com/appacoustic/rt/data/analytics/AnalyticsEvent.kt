package com.appacoustic.rt.data.analytics

interface AnalyticsEvent {
    val name: String
    val parameters: Map<String, Any>
}
