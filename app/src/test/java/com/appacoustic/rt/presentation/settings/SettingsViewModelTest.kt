package com.appacoustic.rt.presentation.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.domain.UserSession
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val userSession = mockk<UserSession>()
    private val analyticsTrackerComponent = mockk<AnalyticsTrackerComponent>(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when the viewModel is initialized, then the test signal is disabled`() {
        val viewModel = buildViewModel()

        val state = viewModel.viewState.value!!
        assertTrue(state is SettingsViewModel.ViewState.Content)
        assertFalse((state as SettingsViewModel.ViewState.Content).testSignalEnabled)
    }

    @Test
    fun `when toggleSwitchTestSignal is triggered, if user session has stored that the test signal is enabled, then send the corresponding view event`() = runTest {
        givenTestSignalEnabled()
        val viewModel = buildViewModel()

        viewModel.toggleSwitchTestSignal()

        testScheduler.advanceUntilIdle()

        val event = viewModel.viewEvents.tryReceive().getOrNull()
        assertTrue(event is SettingsViewModel.ViewEvents.ToggleSwitchTestSignal)
        assertTrue((event as SettingsViewModel.ViewEvents.ToggleSwitchTestSignal).testSignalEnabled)
    }

    @Test
    fun `when toggleSwitchTestSignal is triggered, if user session has stored that the test signal is disabled, then send the corresponding view event`() = runTest {
        givenTestSignalDisabled()
        val viewModel = buildViewModel()

        viewModel.toggleSwitchTestSignal()
        testScheduler.advanceUntilIdle()

        val event = viewModel.viewEvents.tryReceive().getOrNull()
        assertFalse((event as SettingsViewModel.ViewEvents.ToggleSwitchTestSignal).testSignalEnabled)
    }

    private fun givenTestSignalEnabled() {
        every { userSession.isTestSignalEnabled() } returns true
    }

    private fun givenTestSignalDisabled() {
        every { userSession.isTestSignalEnabled() } returns false
    }

    private fun buildViewModel() = SettingsViewModel(
        userSession = userSession,
        analyticsTrackerComponent = analyticsTrackerComponent
    )
}
