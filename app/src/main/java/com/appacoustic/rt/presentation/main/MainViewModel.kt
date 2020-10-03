package com.appacoustic.rt.presentation.main

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.framework.base.viewmodel.StatelessBaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
) : StatelessBaseViewModel<
    MainViewModel.ViewEvents>() {

    init {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToMeasure)
        }
    }

    fun handleShareClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.Share)
        }
    }

    fun handleEmailClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.SendEmail)
        }
    }

    fun handleInfoClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToWeb("http://appacoustic.com"))
        }
    }

    sealed class ViewEvents {
        object NavigateToMeasure : ViewEvents()
        object Share : ViewEvents()
        object SendEmail : ViewEvents()
        data class NavigateToWeb(val uriString: String) : ViewEvents()
    }
}
