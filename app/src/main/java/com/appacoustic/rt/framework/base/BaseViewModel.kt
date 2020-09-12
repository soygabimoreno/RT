package com.appacoustic.rt.framework.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

abstract class BaseViewModel<VIEW_EVENT> : ViewModel() {

    private val _viewEvents = Channel<VIEW_EVENT>()
    val viewEvents: ReceiveChannel<VIEW_EVENT> = _viewEvents

    override fun onCleared() {
        super.onCleared()
        _viewEvents.close()
    }

    protected suspend fun sendViewEvent(viewEvent: VIEW_EVENT) {
        _viewEvents.send(viewEvent)
    }
}
