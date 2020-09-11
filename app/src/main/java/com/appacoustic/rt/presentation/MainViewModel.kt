package com.appacoustic.rt.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appacoustic.rt.framework.event.Event

class MainViewModel(
) : ViewModel() {

    private val _navigateToWeb = MutableLiveData<Event<String>>()
    val navigateToWeb: LiveData<Event<String>> = _navigateToWeb

    init {

    }

    fun handleInfoClicked() {
        _navigateToWeb.value = Event("http://appacoustic.com")
    }
}
