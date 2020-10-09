package com.appacoustic.rt.data.analytics

interface AnalyticsTrackerComponent {
    fun <E : AnalyticsEvent> trackEvent(event: E)
}
