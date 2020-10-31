package com.appacoustic.rt.presentation.settings

import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.extension.exhaustive
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class SettingsFragment : BaseFragment<
    SettingsViewModel.ViewState,
    SettingsViewModel.ViewEvents,
    SettingsViewModel
    >() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override val layoutResId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by koinScope.viewModel(this)

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
        switchTestSignal.setOnCheckedChangeListener { _, filterEnabled ->
            if (filterEnabled) {
                switchTestSignal.setText(R.string.using_dirac_test_signal)
            } else {
                switchTestSignal.setText(R.string.not_using_dirac_test_signal)
            }
            viewModel.handleSwitchFilterChanged(filterEnabled)
        }
    }

    override fun renderViewState(viewState: SettingsViewModel.ViewState) {
        when (viewState) {
            is SettingsViewModel.ViewState.Content -> showContent(viewState)
        }.exhaustive
    }

    private fun showContent(content: SettingsViewModel.ViewState.Content) {
        // TODO
    }

    override fun handleViewEvent(viewEvent: SettingsViewModel.ViewEvents) {
        when (viewEvent) {
            SettingsViewModel.ViewEvents.Foo -> foo()
        }.exhaustive
    }

    private fun foo() {
        // TODO
    }
}
