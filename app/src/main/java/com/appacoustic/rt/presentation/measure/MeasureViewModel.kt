package com.appacoustic.rt.presentation.measure

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.R
import com.appacoustic.rt.domain.Measure
import com.appacoustic.rt.domain.UserSession
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MeasureViewModel(
    private val userSession: UserSession
) : BaseViewModel<
    MeasureViewModel.ViewState,
    MeasureViewModel.ViewEvents>() {

    init {
        updateViewState(ViewState.Loading)
        initContent()
    }

    private fun initContent() {
        val measures = listOf(
            Measure(Measure.Frequency.FREQUENCY_125, 0f),
            Measure(Measure.Frequency.FREQUENCY_250, 0f),
            Measure(Measure.Frequency.FREQUENCY_500, 0.4f),
            Measure(Measure.Frequency.FREQUENCY_1000, 0f),
            Measure(Measure.Frequency.FREQUENCY_2000, 0f),
            Measure(Measure.Frequency.FREQUENCY_4000, 0f),
        )
        updateViewState(
            ViewState.Content(
                textResId = R.string.start,
                measures = measures
            )
        )
    }

    fun handleStartClicked() {
        viewModelScope.launch {
            if (userSession.isRecordAudioPermissionGranted()) {
                sendViewEvent(ViewEvents.ShowUI)
            } else {
                sendViewEvent(ViewEvents.NavigateToPermission)
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        data class Content(
            @StringRes val textResId: Int,
            val measures: List<Measure>
        ) : ViewState()
    }

    sealed class ViewEvents {
        object ShowUI : ViewEvents()
        object NavigateToPermission : ViewEvents()
    }
}
