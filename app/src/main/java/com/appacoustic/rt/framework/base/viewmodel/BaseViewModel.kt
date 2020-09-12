package com.appacoustic.rt.framework.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel<
    VIEW_STATE,
    VIEW_EVENT
    > : StatelessBaseViewModel<VIEW_EVENT>() {

    private val _viewState = MutableLiveData<VIEW_STATE>()
    val viewState: LiveData<VIEW_STATE> = _viewState

    protected fun setViewState(viewState: VIEW_STATE) {
        _viewState.value = viewState
    }

    protected fun getViewState(): VIEW_STATE = _viewState.value!!
}
