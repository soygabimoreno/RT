package com.appacoustic.rt.presentation.main

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
class MainViewModelTest {

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
    fun `when the app starts, then go to measure screen`() {
        testCoroutineScope.runBlockingTest {
            val viewModel = buildViewModel()

            val viewEvents = viewModel.viewEvents
            val event = viewEvents.poll()
            assertTrue(event is MainViewModel.ViewEvents.NavigateToMeasure)
        }
    }

    private fun buildViewModel() = MainViewModel()
}