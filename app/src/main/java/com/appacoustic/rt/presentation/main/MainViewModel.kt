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

    fun handleRateClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.Rate)
        }
    }

    fun handleInfoClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToWeb("http://appacoustic.com"))
        }
    }

    fun handleBottomNavigationMenuMeasureClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToMeasure)
        }
    }

    fun handleBottomNavigationMenuSignalClicked() {
        viewModelScope.launch {
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
