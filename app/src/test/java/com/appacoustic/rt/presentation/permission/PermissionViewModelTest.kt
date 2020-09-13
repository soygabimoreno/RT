package com.appacoustic.rt.presentation.permission

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.left
import arrow.core.right
import com.appacoustic.rt.domain.PermissionRequester
import com.appacoustic.rt.domain.RecordAudioPermissionChecker
import io.mockk.coEvery
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
class PermissionViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val recordAudioPermissionChecker = mockk<RecordAudioPermissionChecker>()

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
    fun `if the audio record permission is granted, then navigate to measure`() {
        givenRecordAudioPermissionGranted()

        val viewModel = buildViewModel()

        val event = viewModel.viewEvents.poll()
        assertTrue(event is PermissionViewModel.ViewEvents.NavigateToMeasure)
    }

    @Test
    fun `if the audio record permission is denied, then show the request record audio permission dialog`() {
        givenRecordAudioPermissionDenied()

        val viewModel = buildViewModel()

        val event = viewModel.viewEvents.poll()
        assertTrue(event is PermissionViewModel.ViewEvents.ShowRecordAudioPermissionRequiredDialog)
    }

    @Test
    fun `if the audio record permission indicates a show rationale, then show it`() {
        givenRecordAudioPermissionShowRationale()

        val viewModel = buildViewModel()

        val event = viewModel.viewEvents.poll()
        assertTrue(event is PermissionViewModel.ViewEvents.ShowRationale)
    }

    @Test
    fun `if the audio record permission triggers an error, then show it`() {
        givenRecordAudioPermissionError()

        val viewModel = buildViewModel()

        val event = viewModel.viewEvents.poll()
        assertTrue(event is PermissionViewModel.ViewEvents.ShowPermissionError)
    }

    private fun givenRecordAudioPermissionGranted() {
        coEvery { recordAudioPermissionChecker() } returns PermissionRequester.PermissionState.GRANTED.right()
    }

    private fun givenRecordAudioPermissionDenied() {
        coEvery { recordAudioPermissionChecker() } returns PermissionRequester.PermissionState.DENIED.right()
    }

    private fun givenRecordAudioPermissionShowRationale() {
        coEvery { recordAudioPermissionChecker() } returns PermissionRequester.PermissionState.SHOW_RATIONALE.right()
    }

    private fun givenRecordAudioPermissionError() {
        coEvery { recordAudioPermissionChecker() } returns PermissionRequester.PermissionRequestException().left()
    }

    private fun buildViewModel() = PermissionViewModel(
        recordAudioPermissionChecker
    )
}
