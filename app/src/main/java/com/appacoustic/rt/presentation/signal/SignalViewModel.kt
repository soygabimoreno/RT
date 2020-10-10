package com.appacoustic.rt.presentation.signal

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficients
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder2
import com.appacoustic.rt.data.filter.butterworth.ButterworthFrequency
import com.appacoustic.rt.data.filter.butterworth.ButterworthOrder
import com.appacoustic.rt.domain.calculator.processing.*
import com.appacoustic.rt.framework.audio.recorder.Recorder
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import com.appacoustic.rt.presentation.signal.analytics.SignalEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignalViewModel(
    private val recorder: Recorder,
    private val analyticsTrackerComponent: AnalyticsTrackerComponent
) : BaseViewModel<
    SignalViewModel.ViewState,
    SignalViewModel.ViewEvents>() {

    init {
        analyticsTrackerComponent.trackEvent(SignalEvents.ScreenSignal)
        updateViewState(
            ViewState.Content(
                x = doubleArrayOf()
            )
        )
    }

    fun handleSwitchFilterChanged(filterEnabled: Boolean) {
        if (filterEnabled) {
            analyticsTrackerComponent.trackEvent(SignalEvents.ClickEnableFilter)
        } else {
            analyticsTrackerComponent.trackEvent(SignalEvents.ClickDisableFilter)
        }
        viewModelScope.launch {
            val butterworthFrequency = (getViewState() as ViewState.Content).butterworthFrequency
            val butterworthOrder = (getViewState() as ViewState.Content).butterworthOrder
            updateContent(
                filterEnabled,
                butterworthFrequency,
                butterworthOrder
            )
        }
    }

    fun handleSpinnerFrequencyChanged(butterworthFrequency: ButterworthFrequency) {
        analyticsTrackerComponent.trackEvent(SignalEvents.ClickSpinnerFrequency(butterworthFrequency.name))
        val butterworthOrder = (getViewState() as ViewState.Content).butterworthOrder
        updateCoefficients(
            butterworthFrequency,
            butterworthOrder
        )
    }

    fun handleSpinnerOrderChanged(butterworthOrder: ButterworthOrder) {
        analyticsTrackerComponent.trackEvent(SignalEvents.ClickSpinnerOrder(butterworthOrder.name))
        val butterworthFrequency = (getViewState() as ViewState.Content).butterworthFrequency
        updateCoefficients(
            butterworthFrequency,
            butterworthOrder
        )
    }

    private fun updateCoefficients(
        butterworthFrequency: ButterworthFrequency,
        butterworthOrder: ButterworthOrder
    ) {
        viewModelScope.launch {
            val filterEnabled = (getViewState() as ViewState.Content).filterEnabled
            updateContent(
                filterEnabled,
                butterworthFrequency,
                butterworthOrder
            )
        }
    }

    private suspend fun updateContent(
        filterEnabled: Boolean,
        butterworthFrequency: ButterworthFrequency,
        butterworthOrder: ButterworthOrder
    ) {
        sendViewEvent(ViewEvents.ShowLoading)
        val x = calculateX(
            filterEnabled,
            butterworthFrequency,
            butterworthOrder
        )
        sendViewEvent(ViewEvents.HideLoading)
        updateViewState(
            (getViewState() as ViewState.Content).copy(
                x,
                filterEnabled,
                butterworthFrequency,
                butterworthOrder
            )
        )
    }

    private suspend fun calculateX(
        filterEnabled: Boolean,
        butterworthFrequency: ButterworthFrequency,
        butterworthOrder: ButterworthOrder
    ): DoubleArray = withContext(Dispatchers.Default) {
        val xBytes = recorder.getXBytes()
        val butterworthCoefficients = getButterworthCoefficients(
            butterworthFrequency,
            butterworthOrder
        )
        val x = if (xBytes.isNotEmpty()) {
            if (filterEnabled) {
                xBytes
                    .toDoubleSamples()
                    .toDivisibleBy32()
                    .normalize()
                    .filterIIR(butterworthCoefficients)
                    .muteStart(0.05, Recorder.SAMPLE_RATE)
            } else {
                xBytes
                    .toDoubleSamples()
                    .toDivisibleBy32()
                    .normalize()
            }
        } else {
            doubleArrayOf()
        }
        return@withContext x
    }

    sealed class ViewState {
        data class Content(
            val x: DoubleArray,
            val filterEnabled: Boolean = false,
            val butterworthFrequency: ButterworthFrequency = ButterworthFrequency.FREQUENCY_125,
            val butterworthOrder: ButterworthOrder = ButterworthOrder.N_2,
            val butterworthCoefficients: ButterworthCoefficients = ButterworthCoefficientsOrder2.FREQUENCY_125
        ) : ViewState()
    }

    sealed class ViewEvents {
        object ShowLoading : ViewEvents()
        object HideLoading : ViewEvents()
    }
}
