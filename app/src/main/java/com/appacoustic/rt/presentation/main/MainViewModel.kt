package com.appacoustic.rt.presentation.main

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.framework.base.viewmodel.StatelessBaseViewModel
import kotlinx.coroutines.launch

class MainViewModel(
) : StatelessBaseViewModel<
    MainViewModel.ViewEvents>() {

    fun handleInfoClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToWeb("http://appacoustic.com"))
        }
    }

    init {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToMeasure)
        }
    }

    sealed class ViewEvents {
        object NavigateToMeasure : ViewEvents()
        data class NavigateToWeb(val uriString: String) : ViewEvents()
    }
}
