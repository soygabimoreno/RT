package com.appacoustic.rt.presentation

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.domain.CheckRecordAudioPermissionUseCase
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val checkRecordAudioPermissionUseCase: CheckRecordAudioPermissionUseCase
) : BaseViewModel<
    MainViewModel.ViewState,
    MainViewModel.ViewEvents>() {

    init {
        updateViewState(ViewState.Loading)
        checkRecordAudioPermission()
    }

    private fun checkRecordAudioPermission() {
        checkRecordAudioPermissionUseCase()
            .fold({
                updateViewState(ViewState.Error)
            }, { granted ->
                if (granted) {
                    showUI()
                } else {
                    showRecordAudioPermissionRequiredDialog()
                }
            })
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
    }
}
