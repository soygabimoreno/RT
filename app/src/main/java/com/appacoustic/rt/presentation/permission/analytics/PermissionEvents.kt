package com.appacoustic.rt.presentation.permission.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val SCREEN_PERMISSION = "SCREEN_PERMISSION"
private const val PERMISSION_GRANTED = "PERMISSION_GRANTED"
private const val PERMISSION_DENIED = "PERMISSION_DENIED"
private const val PERMISSION_SHOW_RATIONALE = "PERMISSION_SHOW_RATIONALE"
private const val PERMISSION_SHOW_APP_SETTINGS = "PERMISSION_SHOW_APP_SETTINGS"

sealed class PermissionEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    object ScreenPermission : PermissionEvents(SCREEN_PERMISSION)
    object PermissionGranted : PermissionEvents(PERMISSION_GRANTED)
    object PermissionDenied : PermissionEvents(PERMISSION_DENIED)
    object PermissionShowRationale : PermissionEvents(PERMISSION_SHOW_RATIONALE)
    object PermissionShowAppSettings : PermissionEvents(PERMISSION_SHOW_APP_SETTINGS)
}
