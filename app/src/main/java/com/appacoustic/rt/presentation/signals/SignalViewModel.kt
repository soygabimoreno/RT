package com.appacoustic.rt.presentation.signals

import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficients
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder2
import com.appacoustic.rt.data.filter.butterworth.ButterworthFrequency
import com.appacoustic.rt.data.filter.butterworth.ButterworthOrder
import com.appacoustic.rt.framework.audio.recorder.Recorder
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel

class SignalViewModel(
    private val recorder: Recorder
) : BaseViewModel<
    SignalViewModel.ViewState,
    SignalViewModel.ViewEvents>() {

    init {
        ViewState.Content(
            xBytes = byteArrayOf()
        )
    }

    fun updateContent() {
        val xBytes = recorder.getXBytes()
        updateViewState(
            ViewState.Content(
                xBytes = xBytes
            )
        )
    }

    fun handleSwitchFilterChanged(filterEnabled: Boolean) {
        updateViewState(
            (getViewState() as ViewState.Content).copy(
                filterEnabled = filterEnabled
            )
        )
    }

    fun handleSpinnerFrequencyChanged(butterworthFrequency: ButterworthFrequency) {
        val butterworthOrder = (getViewState() as ViewState.Content).butterworthOrder
        updateCoefficients(
            butterworthFrequency,
            butterworthOrder
        )
    }

    fun handleSpinnerOrderChanged(butterworthOrder: ButterworthOrder) {
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
        updateViewState(
            (getViewState() as ViewState.Content).copy(
                butterworthCoefficients = getButterworthCoefficients(
                    butterworthFrequency,
                    butterworthOrder
                )
            )
        )
    }

    sealed class ViewState {
        data class Content(
            val xBytes: ByteArray,
            val butterworthFrequency: ButterworthFrequency = ButterworthFrequency.FREQUENCY_125,
            val filterEnabled: Boolean = false,
            val butterworthOrder: ButterworthOrder = ButterworthOrder.N_2,
            val butterworthCoefficients: ButterworthCoefficients = ButterworthCoefficientsOrder2.FREQUENCY_125
        ) : ViewState()
    }

    sealed class ViewEvents {
        object Foo : ViewEvents()
    }
}
