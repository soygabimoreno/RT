package com.appacoustic.rt.data.analytics.error

interface ErrorTrackerComponent {
    fun <E : ErrorEvent> trackError(event: E)

    fun trackError(error: Throwable) {
        trackError(ThrowableErrorEvent(error))
    }
}
