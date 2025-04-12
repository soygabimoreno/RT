package com.appacoustic.rt.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.domain.UserSession
import com.appacoustic.rt.presentation.main.analytics.MainEvents
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val analyticsTrackerComponent = mockk<AnalyticsTrackerComponent>(relaxed = true)
    private val userSession = mockk<UserSession>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when the app starts, then go to measure screen`() = runTest {
        val viewModel = buildViewModel()

        testDispatcher.scheduler.advanceUntilIdle()

        val viewEvents = viewModel.viewEvents
        val event = viewEvents.tryReceive().getOrNull()

        assertTrue(event is MainViewModel.ViewEvents.NavigateToMeasure)
    }

    @Test
    fun `when the user clicks on info, then navigate to the corresponding url`() = runTest {
        val viewModel = buildViewModel()

        viewModel.handleInfoClicked()
        testDispatcher.scheduler.advanceUntilIdle()

        val viewEvents = viewModel.viewEvents
        val firstEvent = viewEvents.tryReceive().getOrNull()
        val secondEvent = viewEvents.tryReceive().getOrNull()

        assertTrue(firstEvent is MainViewModel.ViewEvents.NavigateToMeasure)
        assertTrue(secondEvent is MainViewModel.ViewEvents.NavigateToWeb)
    }

    @Test
    fun `when the screen starts, then its screen analytics event is triggered`() {
        buildViewModel()
        verify(exactly = 1) { analyticsTrackerComponent.trackEvent(MainEvents.ScreenMain) }
    }

    private fun buildViewModel() = MainViewModel(
        userSession = userSession,
        analyticsTrackerComponent = analyticsTrackerComponent
    )
}
