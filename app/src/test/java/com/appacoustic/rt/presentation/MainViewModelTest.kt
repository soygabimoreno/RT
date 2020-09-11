package com.appacoustic.rt.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun `when the user clicks on info, then navigate to the corresponding url`() {
        val initValue = viewModel.navigateToWeb.value
        viewModel.handleInfoClicked()

        val endValue = viewModel.navigateToWeb.value
        assertTrue(initValue != endValue)
    }
}
