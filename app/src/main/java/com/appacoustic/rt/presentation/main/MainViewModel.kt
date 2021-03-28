package com.appacoustic.rt.presentation.main

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.domain.UserSession
import com.appacoustic.rt.framework.base.viewmodel.StatelessBaseViewModel
import com.appacoustic.rt.presentation.main.analytics.MainEvents
import kotlinx.coroutines.launch

class MainViewModel(
    private val userSession: UserSession,
    private val analyticsTrackerComponent: AnalyticsTrackerComponent
) : StatelessBaseViewModel<
    MainViewModel.ViewEvents>() {

    init {
        analyticsTrackerComponent.trackEvent(MainEvents.ScreenMain)
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.NavigateToMeasure)
            sendViewEvent(ViewEvents.NavigateToMeasure(updateContent = false))
        }
    }

    fun handleAuthenticationClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickAuthentication)
            sendViewEvent(ViewEvents.Authentication)
        }
    }

    fun handleHelpClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickHelp)
            sendViewEvent(ViewEvents.Help)
        }
    }

    fun handleShareClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickShare)
            sendViewEvent(ViewEvents.Share)
        }
    }

    fun handleEmailClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickEmail)
            sendViewEvent(ViewEvents.SendEmail)
        }
    }

    fun handleRateClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickRate)
            sendViewEvent(ViewEvents.ExternalRate)
        }
    }

    fun handleInfoClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickInfo)
            sendViewEvent(ViewEvents.NavigateToWeb("http://appacoustic.com"))
        }
    }

    fun handleBottomNavigationMenuMeasureClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickMeasure)
            sendViewEvent(ViewEvents.NavigateToMeasure(updateContent = true))
        }
    }

    fun handleBottomNavigationMenuSignalClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickSignal)
            sendViewEvent(ViewEvents.NavigateToSignal)
        }
    }

    fun handleBottomNavigationMenuPlayerClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickPlayer)
            sendViewEvent(ViewEvents.NavigateToPlayer)
        }
    }

    fun handleBottomNavigationMenuSettingsClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickSettings)
            sendViewEvent(ViewEvents.NavigateToSettings)
        }
    }

    sealed class ViewEvents {
        data class NavigateToMeasure(val updateContent: Boolean) : ViewEvents()
        object NavigateToSignal : ViewEvents()
        object NavigateToPlayer : ViewEvents()
        object NavigateToSettings : ViewEvents()
        object Authentication : ViewEvents()
        object Help : ViewEvents()
        object Share : ViewEvents()
        object SendEmail : ViewEvents()
        object ExternalRate : ViewEvents()
        data class NavigateToWeb(val uriString: String) : ViewEvents()
    }
}
