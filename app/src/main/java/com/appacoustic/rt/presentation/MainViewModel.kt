package com.appacoustic.rt.presentation

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.domain.CheckRecordAudioPermission
import com.appacoustic.rt.framework.base.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
    checkRecordAudioPermission: CheckRecordAudioPermission
) : BaseViewModel<MainViewModel.ViewEvents>() {

    init {
        checkRecordAudioPermission()
    }

    private fun checkRecordAudioPermission() {
    }

    private fun showContent() {
    }

    private fun showRecordAudioPermissionRequiredDialog() {
    }

    fun handleInfoClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToWeb("http://appacoustic.com"))
        }
    }

    sealed class ViewEvents {
        data class NavigateToWeb(val uriString: String) : ViewEvents()
        object ShowContent : ViewEvents()
        object ShowRecordAudioPermissionRequiredDialog : ViewEvents()
    }
}
