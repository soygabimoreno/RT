package com.appacoustic.rt.presentation.measure

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.R
import com.appacoustic.rt.domain.*
import com.appacoustic.rt.framework.audio.recorder.Recorder
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class MeasureViewModel(
    private val recordAudioPermissionChecker: RecordAudioPermissionChecker,
    private val recorder: Recorder,
    private val userSession: UserSession
) : BaseViewModel<
    MeasureViewModel.ViewState,
    MeasureViewModel.ViewEvents>() {

    init {
        updateViewState(ViewState.Loading)
        initContent()
    }

    private fun initContent() {
        viewModelScope.launch {
            recordAudioPermissionChecker().fold({
                userSession.setRecordAudioPermissionGranted(false)
            }, { onPermissionRequested ->
                val recordAudioPermissionGranted = onPermissionRequested == PermissionRequester.PermissionState.GRANTED
                userSession.setRecordAudioPermissionGranted(recordAudioPermissionGranted)
            })
        }
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
                sendViewEvent(ViewEvents.DisableButton)
                ButtonStateHandler(object : ButtonStateHandler.Listener {
                    override fun onTick(textResId: Int) {
                        updateViewState(
                            (getViewState() as ViewState.Content).copy(
                                textResId = textResId
                            )
                        )

                        when (textResId) {
                            R.string.measuring -> {
                                recorder.foo()
                            }
                        }
                    }

                    override fun onFinish(textResId: Int) {
                        viewModelScope.launch {
                            sendViewEvent(ViewEvents.EnableButton)
                            updateViewState(
                                (getViewState() as ViewState.Content).copy(
                                    textResId = textResId
                                )
                            )
                        }
                    }

                    override fun onReduceButtonTextSize() {
                        viewModelScope.launch {
                            sendViewEvent(ViewEvents.ReduceButtonTextSize)
                        }
                    }

                    override fun onAmplifyButtonTextSize() {
                        viewModelScope.launch {
                            sendViewEvent(ViewEvents.AmplifyButtonTextSize)
                        }
                    }
                }).start()
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
        object EnableButton : ViewEvents()
        object DisableButton : ViewEvents()
        object ReduceButtonTextSize : ViewEvents()
        object AmplifyButtonTextSize : ViewEvents()
        object NavigateToPermission : ViewEvents()
    }
}
