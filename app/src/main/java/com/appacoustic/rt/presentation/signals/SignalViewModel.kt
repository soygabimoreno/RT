package com.appacoustic.rt.presentation.signals

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

    sealed class ViewState {
        data class Content(
            val xBytes: ByteArray
        ) : ViewState()
    }

    sealed class ViewEvents {
        object Foo : ViewEvents()
    }
}
