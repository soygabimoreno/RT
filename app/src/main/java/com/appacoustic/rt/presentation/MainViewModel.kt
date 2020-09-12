package com.appacoustic.rt.presentation

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.domain.PermissionRequester
import com.appacoustic.rt.domain.RecordAudioPermissionChecker
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import com.appacoustic.rt.framework.extension.exhaustive
import kotlinx.coroutines.launch

class MainViewModel(
    private val recordAudioPermissionChecker: RecordAudioPermissionChecker
) : BaseViewModel<
    MainViewModel.ViewState,
    MainViewModel.ViewEvents>() {

    init {
        updateViewState(ViewState.Loading)
        checkRecordAudioPermission()
    }

    private fun checkRecordAudioPermission() {
        viewModelScope.launch {
            recordAudioPermissionChecker().fold({
                showPermissionError()
            }, { onPermissionRequested ->
                when (onPermissionRequested) {
                    PermissionRequester.PermissionState.GRANTED -> showUI()
                    PermissionRequester.PermissionState.DENIED -> showRecordAudioPermissionRequiredDialog()
                    PermissionRequester.PermissionState.SHOW_RATIONALE -> showRationale()
                    PermissionRequester.PermissionState.SHOW_APP_SETTINGS -> showAppSettings()
                }.exhaustive
            })
        }
    }

    private fun showUI() {
        viewModelScope.launch {
            updateViewState(ViewState.Content("Lore ipsum"))
            sendViewEvent(ViewEvents.ShowUI)
        }
    }

    private fun showRecordAudioPermissionRequiredDialog() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.ShowRecordAudioPermissionRequiredDialog)
        }
    }

    private fun showRationale() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.ShowRationale)
        }
    }

    private fun showAppSettings() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.ShowAppSettings)
        }
    }

    private fun showPermissionError() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.ShowPermissionError)
        }
    }

    fun handleInfoClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToWeb("http://appacoustic.com"))
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        data class Content(val text: String) : ViewState()
    }

    sealed class ViewEvents {
        data class NavigateToWeb(val uriString: String) : ViewEvents()
        object ShowUI : ViewEvents()
        object ShowRecordAudioPermissionRequiredDialog : ViewEvents()
        object ShowRationale : ViewEvents()
        object ShowPermissionError : ViewEvents()
        object ShowAppSettings : ViewEvents()
    }
}
