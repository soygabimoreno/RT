package com.appacoustic.rt.data.analytics

import com.amplitude.api.AmplitudeClient
import com.appacoustic.rt.framework.KLog
import com.appacoustic.rt.framework.extension.toJSONObject

class AmplitudeAnalyticsTrackerComponent(
    private val amplitudeClient: AmplitudeClient
) : AnalyticsTrackerComponent {
    override fun <E : AnalyticsEvent> trackEvent(event: E) {
        amplitudeClient.logEvent(event.name, event.parameters.toJSONObject())
        KLog.i("${event.name}: ${event.parameters}")
    }
}
