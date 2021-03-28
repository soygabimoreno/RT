package com.appacoustic.rt.presentation.authentication.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val SCREEN_AUTHENTICATION = "SCREEN_AUTHENTICATION"

sealed class AuthenticationEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenAuthentication : AuthenticationEvents(SCREEN_AUTHENTICATION)
}
