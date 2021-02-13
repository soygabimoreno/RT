package com.appacoustic.rt.presentation.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.appacoustic.rt.R
import com.appacoustic.rt.databinding.FragmentSettingsBinding
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.extension.exhaustive
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : BaseFragment<
    FragmentSettingsBinding,
    SettingsViewModel.ViewState,
    SettingsViewModel.ViewEvents,
    SettingsViewModel
    >() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override val viewBinding: (LayoutInflater, ViewGroup?) -> FragmentSettingsBinding = { layoutInflater, viewGroup ->
        FragmentSettingsBinding.inflate(
            layoutInflater,
            viewGroup,
            false
        )
    }

    override val viewModel: SettingsViewModel by viewModel()

    private val accentColor by lazy {
        resources.getColor(
            R.color.accent,
            null
        )
    }
    private val grayLightColor by lazy {
        resources.getColor(
            R.color.gray_light,
            null
        )
    }

    override fun initUI() {
        initSwitchFilter()
    }

    private fun initSwitchFilter() {
        viewModel.toggleSwitchTestSignal()
        binding.switchTestSignal.setOnCheckedChangeListener { _, testSignalEnabled ->
            toggleSwitchTextSignalText(testSignalEnabled)
            viewModel.handleSwitchFilterChanged(testSignalEnabled)
        }
    }

    override fun renderViewState(viewState: SettingsViewModel.ViewState) {
        when (viewState) {
            is SettingsViewModel.ViewState.Content -> showContent(viewState.testSignalEnabled)
        }.exhaustive
    }

    private fun showContent(testSignalEnabled: Boolean) {
        // TODO
    }

    override fun handleViewEvent(viewEvent: SettingsViewModel.ViewEvents) {
        when (viewEvent) {
            is SettingsViewModel.ViewEvents.ToggleSwitchTestSignal -> toggleSwitchTestSignal(viewEvent.testSignalEnabled)
        }.exhaustive
    }

    private fun toggleSwitchTestSignal(testSignalEnabled: Boolean) {
        binding.switchTestSignal.isChecked = testSignalEnabled
        toggleSwitchTextSignalText(testSignalEnabled)
    }

    private fun toggleSwitchTextSignalText(testSignalEnabled: Boolean) {
        with(binding) {
            if (testSignalEnabled) {
                switchTestSignal.setText(R.string.using_dirac_test_signal)
                switchTestSignal.setTextColor(accentColor)
            } else {
                switchTestSignal.setText(R.string.not_using_dirac_test_signal)
                switchTestSignal.setTextColor(grayLightColor)
            }
        }
    }
}
