package com.appacoustic.rt.presentation.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val analyticsTrackerComponent = mockk<AnalyticsTrackerComponent>(relaxed = true)

    private val testSignalEnabled = false

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `when the app starts, then test signal is enabled or disabled depending on the last stored state`() {
        val viewModel = buildViewModel()

        val state = viewModel.viewState.value!!
        assertTrue(state is SettingsViewModel.ViewState.Content)
        assertTrue(testSignalEnabled == (state as SettingsViewModel.ViewState.Content).testSignalEnabled)
    }

    private fun buildViewModel() = SettingsViewModel(
        analyticsTrackerComponent = analyticsTrackerComponent
    )
}
