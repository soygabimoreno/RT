package com.appacoustic.rt.presentation.settings

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.domain.UserSession
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import com.appacoustic.rt.presentation.settings.analytics.SettingsEvents
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userSession: UserSession,
    private val analyticsTrackerComponent: AnalyticsTrackerComponent
) : BaseViewModel<
    SettingsViewModel.ViewState,
    SettingsViewModel.ViewEvents>() {

    init {
        analyticsTrackerComponent.trackEvent(SettingsEvents.ScreenSettings)
        updateViewState(
            ViewState.Content(
                testSignalEnabled = false
            )
        )
    }

    fun toggleSwitchTestSignal() {
        viewModelScope.launch {
            val testSignalEnabled = userSession.isTestSignalEnabled()
            sendViewEvent(ViewEvents.ToggleSwitchTestSignal(testSignalEnabled))
        }
    }

    fun handleSwitchFilterChanged(testSignalEnabled: Boolean) {
        viewModelScope.launch {
            userSession.setTestSignalEnabled(testSignalEnabled)
            if (testSignalEnabled) {
                analyticsTrackerComponent.trackEvent(SettingsEvents.ClickEnableTestSignal)
            } else {
                analyticsTrackerComponent.trackEvent(SettingsEvents.ClickDisableTestSignal)
            }
            updateViewState(
                (getViewState() as ViewState.Content).copy(
                    testSignalEnabled = testSignalEnabled
                )
            )
        }
    }

    sealed class ViewState {
        data class Content(
            val testSignalEnabled: Boolean = false
        ) : ViewState()
    }

    sealed class ViewEvents {
        data class ToggleSwitchTestSignal(val testSignalEnabled: Boolean) : ViewEvents()
    }
}
