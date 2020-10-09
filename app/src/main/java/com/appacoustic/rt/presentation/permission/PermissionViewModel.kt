package com.appacoustic.rt.presentation.permission

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.data.analytics.error.ErrorTrackerComponent
import com.appacoustic.rt.data.analytics.error.NonStandardErrorEvent
import com.appacoustic.rt.domain.PermissionRequester
import com.appacoustic.rt.domain.RecordAudioPermissionChecker
import com.appacoustic.rt.domain.UserSession
import com.appacoustic.rt.framework.base.viewmodel.StatelessBaseViewModel
import com.appacoustic.rt.framework.extension.exhaustive
import kotlinx.coroutines.launch

class PermissionViewModel(
    private val recordAudioPermissionChecker: RecordAudioPermissionChecker,
    private val userSession: UserSession,
    private val errorTracker: ErrorTrackerComponent
) : StatelessBaseViewModel<
    PermissionViewModel.ViewEvents
    >() {

    init {
        checkRecordAudioPermission()
    }

    fun checkRecordAudioPermission() {
        viewModelScope.launch {
            recordAudioPermissionChecker()
                .fold({
                    errorTracker.trackError(NonStandardErrorEvent("PERMISSION_ERROR"))
                    showPermissionError()
                }, { onPermissionRequested ->
                    when (onPermissionRequested) {
                        PermissionRequester.PermissionState.GRANTED -> navigateToMeasure()
                        PermissionRequester.PermissionState.DENIED -> showRecordAudioPermissionRequiredDialog()
                        PermissionRequester.PermissionState.SHOW_RATIONALE -> showRationale()
                        PermissionRequester.PermissionState.SHOW_APP_SETTINGS -> showAppSettings()
                    }.exhaustive
                })
        }
    }

    private fun navigateToMeasure() {
        userSession.setRecordAudioPermissionGranted(true)
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToMeasure)
        }
    }

    private fun showRecordAudioPermissionRequiredDialog() {
        userSession.setRecordAudioPermissionGranted(false)
        viewModelScope.launch {
            sendViewEvent(ViewEvents.ShowRecordAudioPermissionRequiredDialog)
        }
    }

    private fun showRationale() {
        userSession.setRecordAudioPermissionGranted(false)
        viewModelScope.launch {
            sendViewEvent(ViewEvents.ShowRationale)
        }
    }

    private fun showAppSettings() {
        userSession.setRecordAudioPermissionGranted(false)
        viewModelScope.launch {
            sendViewEvent(ViewEvents.ShowAppSettings)
        }
    }

    private fun showPermissionError() {
        userSession.setRecordAudioPermissionGranted(false)
        viewModelScope.launch {
            sendViewEvent(ViewEvents.ShowPermissionError)
        }
    }

    sealed class ViewEvents {
        object NavigateToMeasure : ViewEvents()
        object ShowRecordAudioPermissionRequiredDialog : ViewEvents()
        object ShowRationale : ViewEvents()
        object ShowPermissionError : ViewEvents()
        object ShowAppSettings : ViewEvents()
    }
}
