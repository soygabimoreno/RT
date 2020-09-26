package com.appacoustic.rt.presentation.measure

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.domain.Measure
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
        val measures = listOf(
            Measure(Measure.Frequency.FREQUENCY_125, 0f),
            Measure(Measure.Frequency.FREQUENCY_250, 0f),
            Measure(Measure.Frequency.FREQUENCY_500, 0.4f),
            Measure(Measure.Frequency.FREQUENCY_1000, 0f),
            Measure(Measure.Frequency.FREQUENCY_2000, 0f),
            Measure(Measure.Frequency.FREQUENCY_4000, 0f),
        )
        viewModelScope.launch {
            updateViewState(
                ViewState.Content(
                    text = "Lore ipsum",
                    measures = measures
                )
            )
            sendViewEvent(ViewEvents.ShowUI)
        }
    }

    fun handleStartMeasureClicked() {
        viewModelScope.launch {
            sendViewEvent(ViewEvents.NavigateToPermission)
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        data class Content(
            val text: String,
            val measures: List<Measure>
        ) : ViewState()
    }

    sealed class ViewEvents {
        object ShowUI : ViewEvents()
        object NavigateToPermission : ViewEvents()
    }
}
