package com.appacoustic.rt.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.appacoustic.rt.domain.CheckRecordAudioPermissionUseCase
import io.mockk.every
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

    private val checkRecordAudioPermissionUseCase = mockk<CheckRecordAudioPermissionUseCase>()

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
    fun `if an error happens when the audio record permission is requested, then the screen shows an error`() {
        givenRecordAudioPermissionError()

        val viewModel = buildViewModel()

        val state = viewModel.viewState.value!!
        assertTrue(state is MainViewModel.ViewState.Error)
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
        every { checkRecordAudioPermissionUseCase() } returns Either.right(true)
    }

    private fun givenRecordAudioPermissionDenied() {
        every { checkRecordAudioPermissionUseCase() } returns Either.right(false)
    }

    private fun givenRecordAudioPermissionError() {
        every { checkRecordAudioPermissionUseCase() } returns Either.left(Exception("Foo"))
    }

    private fun buildViewModel() = MainViewModel(
        checkRecordAudioPermissionUseCase
    )
}
