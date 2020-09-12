package com.appacoustic.rt.presentation.measure

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MeasureViewModel(
) : BaseViewModel<
    MeasureViewModel.ViewState,
    MeasureViewModel.ViewEvents>() {

    init {
        updateViewState(ViewState.Loading)
        showUI()
    }

    private fun showUI() {
        viewModelScope.launch {
            updateViewState(ViewState.Content("Lore ipsum"))
            sendViewEvent(ViewEvents.ShowUI)
        }
    }

    fun handleStartMeasureClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToPermission)
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
        object ShowUI : ViewEvents()
        object NavigateToPermission : ViewEvents()
        data class NavigateToWeb(val uriString: String) : ViewEvents()
    }
}
