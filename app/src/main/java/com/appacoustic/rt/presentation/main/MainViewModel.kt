package com.appacoustic.rt.presentation.main

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.framework.base.viewmodel.StatelessBaseViewModel
import com.appacoustic.rt.presentation.main.analytics.MainEvents
import kotlinx.coroutines.launch

class MainViewModel(
    private val analyticsTrackerComponent: AnalyticsTrackerComponent
) : StatelessBaseViewModel<
    MainViewModel.ViewEvents>() {

    init {
        analyticsTrackerComponent.trackEvent(MainEvents.ScreenMain)
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.NavigateToMeasure)
            sendViewEvent(ViewEvents.NavigateToMeasure)
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
            sendViewEvent(ViewEvents.Rate)
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
            sendViewEvent(ViewEvents.NavigateToMeasure)
        }
    }

    fun handleBottomNavigationMenuSignalClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MainEvents.ClickSignal)
            sendViewEvent(ViewEvents.NavigateToSignal)
        }
    }

    sealed class ViewEvents {
        object NavigateToMeasure : ViewEvents()
        object NavigateToSignal : ViewEvents()
        object Share : ViewEvents()
        object SendEmail : ViewEvents()
        object Rate : ViewEvents()
        data class NavigateToWeb(val uriString: String) : ViewEvents()
    }
}
