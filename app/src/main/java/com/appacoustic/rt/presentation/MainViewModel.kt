package com.appacoustic.rt.presentation

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.domain.CheckRecordAudioPermission
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    checkRecordAudioPermission: CheckRecordAudioPermission
) : BaseViewModel<
    MainViewModel.ViewState,
    MainViewModel.ViewEvents>() {

    init {
        checkRecordAudioPermission()
        showUI()
    }

    private fun checkRecordAudioPermission() {
    }

    private fun showUI() {
        setViewState(ViewState.Content("Foo"))
    }

    private fun showRecordAudioPermissionRequiredDialog() {
    }

    fun handleInfoClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToWeb("http://appacoustic.com"))
        }
    }

    sealed class ViewState {
        data class Content(val text: String) : ViewState()
    }

    sealed class ViewEvents {
        data class NavigateToWeb(val uriString: String) : ViewEvents()
        object ShowUI : ViewEvents()
        object ShowRecordAudioPermissionRequiredDialog : ViewEvents()
    }
}
