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
            sendViewEvent(ViewEvents.NavigateRegister)
        }
    }

    sealed class ViewEvents {
        object NavigateRegister : ViewEvents()
    }
}
