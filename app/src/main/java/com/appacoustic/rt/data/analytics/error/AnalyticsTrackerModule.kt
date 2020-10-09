package com.appacoustic.rt.data.analytics.error

import com.amplitude.api.Amplitude
import com.appacoustic.rt.data.analytics.AmplitudeAnalyticsTrackerComponent
import com.appacoustic.rt.data.analytics.AnalyticsTracker
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import org.koin.dsl.module

val analyticsTrackerModule = module {
    single { Amplitude.getInstance() }

    single<AnalyticsTrackerComponent> {
        AnalyticsTracker(
            listOf(
                AmplitudeAnalyticsTrackerComponent(
                    amplitudeClient = get()
                )
            )
        )
    }
}
