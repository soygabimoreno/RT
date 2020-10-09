package com.appacoustic.rt.data.analytics.error

import com.appacoustic.rt.domain.UserSession
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashlyticsErrorTrackerComponent(
    private val crashlytics: FirebaseCrashlytics,
    private val userSession: UserSession
) : ErrorTrackerComponent {

    override fun <E : ErrorEvent> trackError(event: E) {
        with(crashlytics) {
            val eventWithCommonAttributes = addCommonAttributes(event)

            eventWithCommonAttributes.parameters.forEach { (key, value) ->
                setCustomKey(key, value.toString())
            }

            when (event) {
                is NonStandardErrorEvent -> recordException(Exception(event.tag))
                is ThrowableErrorEvent -> recordException(event.throwable)
            }
        }
    }

    private fun <E : ErrorEvent> addCommonAttributes(event: E): ErrorEvent {
        val isRecordAudioPermissionGranted = userSession.isRecordAudioPermissionGranted()

        val commonAttributes = mapOf(
            "isRecordAudioPermissionGranted" to isRecordAudioPermissionGranted
        )

        return when (event) {
            is NonStandardErrorEvent -> event.copy(parameters = event.parameters + commonAttributes)
            is ThrowableErrorEvent -> event.copy(parameters = event.parameters + commonAttributes)
            else -> object : ErrorEvent {
                override val parameters: Map<String, Any> = event.parameters + commonAttributes
            }
        }
    }
}