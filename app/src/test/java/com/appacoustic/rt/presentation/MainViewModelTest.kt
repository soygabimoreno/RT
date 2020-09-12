package com.appacoustic.rt.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.appacoustic.rt.domain.CheckRecordAudioPermission
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

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        val checkRecordAudioPermission = CheckRecordAudioPermission()
        viewModel = MainViewModel(
            checkRecordAudioPermission = checkRecordAudioPermission
        )
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `when the user clicks on info, then navigate to the corresponding url`() {
        testCoroutineScope.runBlockingTest {
            viewModel.handleInfoClicked()

            val event = viewModel.viewEvents.poll()
            assertTrue(event is MainViewModel.ViewEvents.NavigateToWeb)
        }
    }
}
