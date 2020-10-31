package com.appacoustic.rt.data.analytics.error

import com.appacoustic.rt.domain.UserSession
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CrashlyticsErrorTrackerComponent(
    private val crashlytics: FirebaseCrashlytics,
    private val userSession: UserSession
) : ErrorTrackerComponent {

    override fun <E : ErrorEvent> trackError(event: E) {
        with(crashlytics) {
            GlobalScope.launch {
                val eventWithCommonAttributes = addCommonAttributes(event)
                eventWithCommonAttributes.parameters.forEach { (key, value) ->
                    setCustomKey(
                        key,
                        value.toString()
                    )
                }

                when (event) {
                    is NonStandardErrorEvent -> recordException(Exception(event.tag))
                    is ThrowableErrorEvent -> recordException(event.throwable)
                }
            }
        }
    }

    private suspend fun <E : ErrorEvent> addCommonAttributes(event: E): ErrorEvent {
        val isRecordAudioPermissionGranted = userSession.isRecordAudioPermissionGranted()
        val testSignalEnabled = userSession.isTestSignalEnabled()
        val commonParameters = mapOf(
            "isRecordAudioPermissionGranted" to isRecordAudioPermissionGranted,
            "testSignalEnabled" to testSignalEnabled
        )
        val parameters = event.parameters + commonParameters
        return when (event) {
            is NonStandardErrorEvent -> event.copy(parameters = parameters)
            is ThrowableErrorEvent -> event.copy(parameters = parameters)
            else -> object : ErrorEvent {
                override val parameters: Map<String, Any> = parameters
            }
        }
    }
}
