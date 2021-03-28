package com.appacoustic.rt.presentation.authentication

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.domain.UserSession
import com.appacoustic.rt.framework.base.viewmodel.StatelessBaseViewModel
import com.appacoustic.rt.presentation.authentication.analytics.AuthenticationEvents
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val userSession: UserSession,
    private val analyticsTrackerComponent: AnalyticsTrackerComponent
) : StatelessBaseViewModel<
    AuthenticationViewModel.ViewEvents>() {

    init {
        analyticsTrackerComponent.trackEvent(AuthenticationEvents.ScreenAuthentication)
        viewModelScope.launch {
            sendViewEvent(ViewEvents.Foo)
        }
    }

    sealed class ViewEvents {
        object Foo : ViewEvents()
    }
}
