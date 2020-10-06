package com.appacoustic.rt.presentation.measure

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.R
import com.appacoustic.rt.domain.*
import com.appacoustic.rt.framework.audio.recorder.Recorder
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import com.appacoustic.rt.framework.mapper.toStringResId
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
            Measure(Measure.Frequency.FREQUENCY_500, 0f),
            Measure(Measure.Frequency.FREQUENCY_1000, 0f),
            Measure(Measure.Frequency.FREQUENCY_2000, 0f),
            Measure(Measure.Frequency.FREQUENCY_4000, 0f),
        )
        updateViewState(
            ViewState.Content(
                textResId = R.string.start,
                measures = measures,
                averageReverbTime = 0f
            )
        )
    }

    private fun calculateAverageReverbTime(measures: List<Measure>): Float =
        (measures[4].time + measures[5].time) / 2

    fun handleStartClicked() {
        viewModelScope.launch {
            if (userSession.isRecordAudioPermissionGranted()) {
                sendViewEvent(ViewEvents.DisableButton)
                ButtonStateHandler(object : ButtonStateHandler.Listener {
                    override fun onTick(state: ButtonStateHandler.State) {
                        updateViewState(
                            (getViewState() as ViewState.Content).copy(
                                textResId = state.toStringResId()
                            )
                        )

                        when (state) {
                            ButtonStateHandler.State.MEASURING -> {
                                recorder.start()
                            }
                        }
                    }

                    override fun onFinish(state: ButtonStateHandler.State) {
                        viewModelScope.launch {
                            sendViewEvent(ViewEvents.EnableButton)
                            recorder.stop {
                                it.fold({
                                    viewModelScope.launch {
                                        sendViewEvent(ViewEvents.EmptySignalError)
                                        updateViewState(
                                            (getViewState() as ViewState.Content).copy(
                                                textResId = state.toStringResId()
                                            )
                                        )
                                    }
                                }, { measures ->
                                    updateViewState(
                                        (getViewState() as ViewState.Content).copy(
                                            textResId = state.toStringResId(),
                                            measures = measures,
                                            averageReverbTime = calculateAverageReverbTime(measures)
                                        )
                                    )
                                })
                            }
                            val xBytes = recorder.getXBytes()
                            updateViewState(
                                (getViewState() as ViewState.Content).copy(
                                    xBytes = xBytes
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
            val measures: List<Measure>,
            val averageReverbTime: Float,
            val xBytes: ByteArray = ByteArray(0)
        ) : ViewState()
    }

    sealed class ViewEvents {
        object EmptySignalError : ViewEvents()
        object EnableButton : ViewEvents()
        object DisableButton : ViewEvents()
        object ReduceButtonTextSize : ViewEvents()
        object AmplifyButtonTextSize : ViewEvents()
        object NavigateToPermission : ViewEvents()
    }
}
