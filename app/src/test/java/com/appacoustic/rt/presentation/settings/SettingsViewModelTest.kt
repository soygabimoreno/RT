package com.appacoustic.rt.presentation.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.domain.UserSession
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
class SettingsViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

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
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Ignore
    @Test
    fun `when the viewModel is initialized, if user session has stored that the test signal is enabled, then test signal is enabled`() {
        givenTestSignalEnabled()
        val viewModel = buildViewModel()

        val state = viewModel.viewState.value!!
        assertTrue(state is SettingsViewModel.ViewState.Content)
        assertTrue((state as SettingsViewModel.ViewState.Content).testSignalEnabled)
    }

    @Test
    fun `when the viewModel is initialized, if user session has stored that the test signal is disabled, then test signal is disabled`() {
        givenTestSignalDisabled()
        val viewModel = buildViewModel()

        val state = viewModel.viewState.value!!
        assertTrue(state is SettingsViewModel.ViewState.Content)
        assertFalse((state as SettingsViewModel.ViewState.Content).testSignalEnabled)
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
