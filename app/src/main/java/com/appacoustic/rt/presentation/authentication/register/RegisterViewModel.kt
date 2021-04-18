package com.appacoustic.rt.presentation.authentication.register

import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.data.analytics.error.ErrorTrackerComponent
import com.appacoustic.rt.domain.UserSession
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import com.appacoustic.rt.presentation.authentication.register.analytics.RegisterEvents
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userSession: UserSession,
    private val analyticsTrackerComponent: AnalyticsTrackerComponent,
    private val errorTrackerComponent: ErrorTrackerComponent
) : BaseViewModel<
    RegisterViewModel.ViewState,
    RegisterViewModel.ViewEvents>() {

    init {
        analyticsTrackerComponent.trackEvent(RegisterEvents.ScreenRegister)
        updateViewState(ViewState.Loading)
        initContent()
    }

    private fun initContent() {
        viewModelScope.launch {
            updateViewState(ViewState.Content(0))
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        data class Content(
            val foo: Int,
        ) : ViewState()
    }

    sealed class ViewEvents {
        object Foo : ViewEvents()
    }
}
