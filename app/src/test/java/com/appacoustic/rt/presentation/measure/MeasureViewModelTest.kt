package com.appacoustic.rt.presentation.measure

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MeasureViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
    fun `when the user clicks on info, then navigate to the corresponding url`() {
        testCoroutineScope.runBlockingTest {
            val viewModel = buildViewModel()

            viewModel.handleInfoClicked()

            val viewEvents = viewModel.viewEvents
            val firstEvent = viewEvents.poll()
            val secondEvent = viewEvents.poll()

            assertTrue(firstEvent is MeasureViewModel.ViewEvents.ShowUI)
            assertTrue(secondEvent is MeasureViewModel.ViewEvents.NavigateToWeb)
        }
    }

    private fun buildViewModel() = MeasureViewModel()
}
