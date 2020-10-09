package com.appacoustic.rt.data.analytics

class AnalyticsTracker(
    private val components: List<AnalyticsTrackerComponent>
) : AnalyticsTrackerComponent {
    override fun <E : AnalyticsEvent> trackEvent(event: E) {
        components.forEach { it.trackEvent(event) }
    }
}
