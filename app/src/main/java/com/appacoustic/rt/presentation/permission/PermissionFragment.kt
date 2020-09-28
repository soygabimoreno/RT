package com.appacoustic.rt.presentation.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.fragment.StatelessBaseFragment
import com.appacoustic.rt.framework.customview.InfoAlertDialog
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
            PermissionViewModel.ViewEvents.ShowRationale -> showRecordAudioPermissionRequiredDialog()
            PermissionViewModel.ViewEvents.ShowAppSettings -> showAppSettings()
        }.exhaustive
    }

    private fun showRecordAudioPermissionRequiredDialog() {
        InfoAlertDialog.Builder(requireContext())
            .drawable(R.drawable.ic_baseline_perm_camera_mic_24)
            .title(R.string.dialog_info_title)
            .description(R.string.dialog_info_description)
            .btnText(R.string.dialog_info_btn_text)
            .onButtonClicked {
                viewModel.checkRecordAudioPermission()
            }
            .cancelable(false)
            .buildAndShow()
    }

    private fun showPermissionError() {
        debugToast("showPermissionError")
    }

    private fun showRationale() {
        debugToast("showRationale")
    }

    private fun showAppSettings() {
        InfoAlertDialog.Builder(requireContext())
            .drawable(R.drawable.ic_baseline_settings_applications_24)
            .title(R.string.dialog_app_settings_title)
            .description(R.string.dialog_app_settings_description)
            .btnText(R.string.dialog_app_settings_btn_text)
            .onButtonClicked {
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + requireContext().applicationContext.packageName)
                ).apply {
                    addCategory("android.intent.category.DEFAULT")
                    startActivity(this)
                }
            }
            .cancelable(false)
            .buildAndShow()
    }
}
