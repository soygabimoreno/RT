package com.appacoustic.rt.presentation.measure

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.appacoustic.rt.R
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.data.analytics.error.ErrorTrackerComponent
import com.appacoustic.rt.data.analytics.error.NonStandardErrorEvent
import com.appacoustic.rt.domain.*
import com.appacoustic.rt.framework.audio.recorder.Recorder
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel
import com.appacoustic.rt.framework.mapper.toStringResId
import com.appacoustic.rt.presentation.measure.analytics.MeasureEvents
import kotlinx.coroutines.launch

class MeasureViewModel(
    private val recordAudioPermissionChecker: RecordAudioPermissionChecker,
    private val recorder: Recorder,
    private val userSession: UserSession,
    private val analyticsTrackerComponent: AnalyticsTrackerComponent,
    private val errorTrackerComponent: ErrorTrackerComponent
) : BaseViewModel<
    MeasureViewModel.ViewState,
    MeasureViewModel.ViewEvents>() {

    init {
        analyticsTrackerComponent.trackEvent(MeasureEvents.ScreenMeasure)
        updateViewState(ViewState.Loading)
        initContent()
    }

    private fun initContent() {
        viewModelScope.launch {
            recordAudioPermissionChecker().fold(
                {
                    userSession.setRecordAudioPermissionGranted(false)
                },
                { onPermissionRequested ->
                    val recordAudioPermissionGranted = onPermissionRequested == PermissionRequester.PermissionState.GRANTED
                    userSession.setRecordAudioPermissionGranted(recordAudioPermissionGranted)
                })
        }
        val measures = listOf(
            Measure(
                Measure.Frequency.FREQUENCY_125,
                0f
            ),
            Measure(
                Measure.Frequency.FREQUENCY_250,
                0f
            ),
            Measure(
                Measure.Frequency.FREQUENCY_500,
                0f
            ),
            Measure(
                Measure.Frequency.FREQUENCY_1000,
                0f
            ),
            Measure(
                Measure.Frequency.FREQUENCY_2000,
                0f
            ),
            Measure(
                Measure.Frequency.FREQUENCY_4000,
                0f
            ),
        )
        updateViewState(
            ViewState.Content(
                textResId = R.string.start,
                measures = measures,
                averageReverbTime = 0f
            )
        )
    }

    fun updateContent() {
        recorder.calculateReverbTime()
            .fold(
                { exception ->
                    errorTrackerComponent.trackError(exception)
                },
                { measures ->
                    updateViewState(
                        (getViewState() as ViewState.Content).copy(
                            measures = measures,
                            averageReverbTime = calculateAverageReverbTime(measures)
                        )
                    )
                })
    }

    fun handleStartClicked() {
        viewModelScope.launch {
            analyticsTrackerComponent.trackEvent(MeasureEvents.ClickStart)
            if (userSession.isRecordAudioPermissionGranted()) {
                sendViewEvent(ViewEvents.EnableScreen(false))
                ButtonStateHandler(object : ButtonStateHandler.Listener {
                    private var recordingStarted = false

                    override fun onTick(state: ButtonStateHandler.State) {
                        analyticsTrackerComponent.trackEvent(MeasureEvents.ButtonState(state.name))
                        updateViewState(
                            (getViewState() as ViewState.Content).copy(
                                textResId = state.toStringResId()
                            )
                        )

                        when (state) {
                            ButtonStateHandler.State.MEASURING -> {
                                if (!recordingStarted) {
                                    recorder.start()
                                    recordingStarted = true
                                }
                            }
                        }
                    }

                    override fun onFinish(state: ButtonStateHandler.State) {
                        viewModelScope.launch {
                            analyticsTrackerComponent.trackEvent(MeasureEvents.ButtonState(state.name))
                            sendViewEvent(ViewEvents.EnableScreen(true))
                            recordingStarted = false
                            recorder.stop()
                                .fold(
                                    { exception ->
                                        errorTrackerComponent.trackError(
                                            NonStandardErrorEvent(
                                                "EMPTY_SIGNAL_ERROR",
                                                mapOf("exception" to exception)
                                            )
                                        )
                                        viewModelScope.launch {
                                            sendViewEvent(ViewEvents.EmptySignalError)
                                            updateViewState(
                                                (getViewState() as ViewState.Content).copy(
                                                    textResId = state.toStringResId()
                                                )
                                            )
                                        }
                                    },
                                    { measures ->
                                        analyticsTrackerComponent.trackEvent(MeasureEvents.DataMeasures(measures.toHumanReadable()))
                                        updateViewState(
                                            (getViewState() as ViewState.Content).copy(
                                                textResId = state.toStringResId(),
                                                measures = measures,
                                                averageReverbTime = calculateAverageReverbTime(measures)
                                            )
                                        )
                                        checkRightMeasuresAndTryInAppRating(measures)
                                    })
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

    private fun calculateAverageReverbTime(measures: List<Measure>): Float =
        (measures[4].time + measures[5].time) / 2

    private fun checkRightMeasuresAndTryInAppRating(measures: List<Measure>) {
        val size = measures.size
        var count = 0
        measures.forEach { measure ->
            if (measure.time < 1.3) {
                count++
            }
        }
        if (count == size) {
            viewModelScope.launch {
                sendViewEvent(ViewEvents.TryInAppRating)
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Error : ViewState()
        data class Content(
            @StringRes val textResId: Int,
            val measures: List<Measure>,
            val averageReverbTime: Float
        ) : ViewState()
    }

    sealed class ViewEvents {
        object EmptySignalError : ViewEvents()
        data class EnableScreen(val enable: Boolean) : ViewEvents()
        object ReduceButtonTextSize : ViewEvents()
        object AmplifyButtonTextSize : ViewEvents()
        object NavigateToPermission : ViewEvents()
        object TryInAppRating : ViewEvents()
    }
}
