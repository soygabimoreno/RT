package com.appacoustic.rt.presentation.measure

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.right
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.data.analytics.error.ErrorTrackerComponent
import com.appacoustic.rt.domain.Measure
import com.appacoustic.rt.domain.PermissionRequester
import com.appacoustic.rt.domain.RecordAudioPermissionChecker
import com.appacoustic.rt.domain.UserSession
import com.appacoustic.rt.framework.audio.recorder.Recorder
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@ExperimentalCoroutinesApi
class MeasureViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    private val recordAudioPermissionChecker = mockk<RecordAudioPermissionChecker>(relaxed = true)
    private val recorder = mockk<Recorder>(relaxed = true)
    private val userSession = mockk<UserSession>(relaxed = true)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val analyticsTrackerComponent = mockk<AnalyticsTrackerComponent>(relaxed = true)
    private val errorTrackerComponent = mockk<ErrorTrackerComponent>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Ignore("This test is not valid anymore because updateContent does not trigger the in-app review")
    @Test
    fun `if measures are right, then try to perform an in-app review view event`() {
        givenRecordAudioPermissionGranted()
        givenRightMeasures()
        val viewModel = buildViewModel()

        viewModel.updateContent()

        val event = viewModel.viewEvents.poll()
        assertTrue(event is MeasureViewModel.ViewEvents.TryInAppRating)
    }

    @Ignore("This test is not valid anymore because updateContent does not trigger the in-app review")
    @Test
    fun `if measures are wrong, then trying in-app review view event is not triggered`() {
        givenRecordAudioPermissionGranted()
        givenWrongMeasures()
        val viewModel = buildViewModel()

        viewModel.updateContent()

        val event = viewModel.viewEvents.poll()
        assertFalse(event is MeasureViewModel.ViewEvents.TryInAppRating)
    }

    private fun givenRecordAudioPermissionGranted() {
        every { userSession.isRecordAudioPermissionGranted() } returns true
        coEvery { recordAudioPermissionChecker() } returns PermissionRequester.PermissionState.GRANTED.right()
    }

    private fun givenRightMeasures() {
        every { recorder.calculateReverbTime() } returns buildRightMeasures().right()
    }

    private fun givenWrongMeasures() {
        every { recorder.calculateReverbTime() } returns buildWrongMeasures().right()
    }

    private fun buildRightMeasures() = listOf(
        Measure(
            Measure.Frequency.FREQUENCY_125,
            0.4f
        ),
        Measure(
            Measure.Frequency.FREQUENCY_250,
            0.3f
        ),
        Measure(
            Measure.Frequency.FREQUENCY_500,
            0.2f
        ),
        Measure(
            Measure.Frequency.FREQUENCY_1000,
            0.1f
        ),
        Measure(
            Measure.Frequency.FREQUENCY_2000,
            0.7f
        ),
        Measure(
            Measure.Frequency.FREQUENCY_4000,
            1.2f
        )
    )

    private fun buildWrongMeasures() = listOf(
        Measure(
            Measure.Frequency.FREQUENCY_125,
            2.4f
        ),
        Measure(
            Measure.Frequency.FREQUENCY_250,
            0.3f
        ),
        Measure(
            Measure.Frequency.FREQUENCY_500,
            0.2f
        ),
        Measure(
            Measure.Frequency.FREQUENCY_1000,
            0.1f
        ),
        Measure(
            Measure.Frequency.FREQUENCY_2000,
            0.7f
        ),
        Measure(
            Measure.Frequency.FREQUENCY_4000,
            1.2f
        )
    )

    private fun buildViewModel() = MeasureViewModel(
        recordAudioPermissionChecker = recordAudioPermissionChecker,
        recorder = recorder,
        userSession = userSession,
        analyticsTrackerComponent = analyticsTrackerComponent,
        errorTrackerComponent = errorTrackerComponent
    )
}
