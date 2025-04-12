package com.appacoustic.rt.presentation.player

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.framework.audio.player.Player
import com.appacoustic.rt.presentation.player.analytics.PlayerEvents
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PlayerViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val player = mockk<Player>(relaxed = true)
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
    fun `when an item is pressed, then the player is called`() {
        val viewModel = buildViewModel()

        val filename = "abdc"
        val rawResId = 1234
        viewModel.handlePlayButtonClicked(
            filename,
            rawResId
        )

        verify(exactly = 1) { player.start(any()) }
    }

    @Test
    fun `when an item is pressed, then its analytics event is triggered`() {
        val viewModel = buildViewModel()

        val filename = "abdc"
        val rawResId = 1234
        viewModel.handlePlayButtonClicked(
            filename,
            rawResId
        )

        val slot = slot<PlayerEvents.ClickPlayTestSignal>()
        verify(exactly = 1) { analyticsTrackerComponent.trackEvent(capture(slot)) }
        Assert.assertTrue(filename == slot.captured.parameters["FILENAME"])
    }

    private fun buildViewModel() = PlayerViewModel(
        player = player,
        analyticsTrackerComponent = analyticsTrackerComponent
    )
}
