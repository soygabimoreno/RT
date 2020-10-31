package com.appacoustic.rt.presentation.settings

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import com.appacoustic.rt.presentation.settings.analytics.SettingsEvents
import kotlinx.coroutines.launch

class SettingsViewModel(
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

    fun handleSwitchFilterChanged(testSignalEnabled: Boolean) {
        if (testSignalEnabled) {
            analyticsTrackerComponent.trackEvent(SettingsEvents.ClickEnableTestSignal)
        } else {
            analyticsTrackerComponent.trackEvent(SettingsEvents.ClickDisableTestSignal)
        }
        viewModelScope.launch {
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
        object Foo : ViewEvents()
    }
}
