package com.appacoustic.rt.presentation.authentication.register.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val SCREEN_REGISTER = "SCREEN_REGISTER"

sealed class RegisterEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenRegister : RegisterEvents(SCREEN_REGISTER)
}
