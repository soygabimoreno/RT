package com.appacoustic.rt.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.left
import arrow.core.right
import com.appacoustic.rt.domain.PermissionRequester
import com.appacoustic.rt.domain.RecordAudioPermissionChecker
import io.mockk.coEvery
import io.mockk.mockk
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
    fun `if the audio record permission is granted, then show UI`() {
        givenRecordAudioPermissionGranted()

        val viewModel = buildViewModel()

        val event = viewModel.viewEvents.poll()
        assertTrue(event is MainViewModel.ViewEvents.ShowUI)
    }

    @Test
    fun `if the audio record permission is denied, then show the request record audio permission dialog`() {
        givenRecordAudioPermissionDenied()

        val viewModel = buildViewModel()

        val event = viewModel.viewEvents.poll()
        assertTrue(event is MainViewModel.ViewEvents.ShowRecordAudioPermissionRequiredDialog)
    }

    @Test
    fun `if the audio record permission indicates a show rationale, then show it`() {
        givenRecordAudioPermissionShowRationale()

        val viewModel = buildViewModel()

        val event = viewModel.viewEvents.poll()
        assertTrue(event is MainViewModel.ViewEvents.ShowRationale)
    }

    @Test
    fun `if the audio record permission triggers an error, then show it`() {
        givenRecordAudioPermissionError()

        val viewModel = buildViewModel()

        val event = viewModel.viewEvents.poll()
        assertTrue(event is MainViewModel.ViewEvents.ShowPermissionError)
    }

    @Test
    fun `if the audio record permission is granted, then the content is shown`() {
        givenRecordAudioPermissionGranted()

        val viewModel = buildViewModel()

        val state = viewModel.viewState.value!!
        assertTrue(state is MainViewModel.ViewState.Content)
    }

    @Test
    fun `if the audio record permission is denied, then the screen remains Loading`() {
        givenRecordAudioPermissionDenied()

        val viewModel = buildViewModel()

        val state = viewModel.viewState.value!!
        assertTrue(state is MainViewModel.ViewState.Loading)
    }

    @Test
    fun `when the user clicks on info, then navigate to the corresponding url`() {
        testCoroutineScope.runBlockingTest {
            givenRecordAudioPermissionGranted()
            val viewModel = buildViewModel()

            viewModel.handleInfoClicked()

            val viewEvents = viewModel.viewEvents
            val firstEvent = viewEvents.poll()
            val secondEvent = viewEvents.poll()

            assertTrue(firstEvent is MainViewModel.ViewEvents.ShowUI)
            assertTrue(secondEvent is MainViewModel.ViewEvents.NavigateToWeb)
        }
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

    private fun buildViewModel() = MainViewModel(
        recordAudioPermissionChecker
    )
}
