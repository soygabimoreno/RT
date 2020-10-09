package com.appacoustic.rt.presentation.measure

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.data.analytics.error.ErrorTrackerComponent
import com.appacoustic.rt.domain.RecordAudioPermissionChecker
import com.appacoustic.rt.domain.UserSession
import com.appacoustic.rt.framework.audio.recorder.Recorder
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

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

    private fun buildViewModel() = MeasureViewModel(
        recordAudioPermissionChecker = recordAudioPermissionChecker,
        recorder = recorder,
        userSession = userSession,
        analyticsTrackerComponent = analyticsTrackerComponent,
        errorTrackerComponent = errorTrackerComponent
    )

    private fun givenRecordAudioPermissionGranted() {
        every { userSession.isRecordAudioPermissionGranted() } returns true
    }

    private fun givenRecordAudioPermissionNotGranted() {
        every { userSession.isRecordAudioPermissionGranted() } returns false
    }
}
