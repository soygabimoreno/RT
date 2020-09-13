package com.appacoustic.rt.presentation.permission

import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.fragment.StatelessBaseFragment
import com.appacoustic.rt.framework.extension.debugToast
import com.appacoustic.rt.framework.extension.exhaustive
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class PermissionFragment : StatelessBaseFragment<
    PermissionViewModel.ViewEvents,
    PermissionViewModel
    >() {

    companion object {
        fun newInstance(
            navigateToMeasure: () -> Unit
        ): PermissionFragment =
            PermissionFragment().apply {
                this.navigateToMeasure = navigateToMeasure
            }
    }

    private lateinit var navigateToMeasure: () -> Unit

    override val layoutResId = R.layout.fragment_permission
    override val viewModel: PermissionViewModel by koinScope.viewModel(this)

    override fun initUI() {}

    override fun handleViewEvent(viewEvent: PermissionViewModel.ViewEvents) {
        when (viewEvent) {
            PermissionViewModel.ViewEvents.NavigateToMeasure -> navigateToMeasure()
            PermissionViewModel.ViewEvents.ShowRecordAudioPermissionRequiredDialog -> showRecordAudioPermissionRequiredDialog()
            PermissionViewModel.ViewEvents.ShowPermissionError -> showPermissionError()
            PermissionViewModel.ViewEvents.ShowRationale -> showRationale()
            PermissionViewModel.ViewEvents.ShowAppSettings -> showAppSettings()
        }.exhaustive
    }

    private fun showRecordAudioPermissionRequiredDialog() {
        debugToast("showRecordAudioPermissionRequiredDialog")
    }

    private fun showPermissionError() {
        debugToast("showPermissionError")
    }

    private fun showRationale() {
        debugToast("showRationale")
    }

    private fun showAppSettings() {
        debugToast("showAppSettings")
    }
}
