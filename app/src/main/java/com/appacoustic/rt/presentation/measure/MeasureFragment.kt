package com.appacoustic.rt.presentation.measure

import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.extension.debugToast
import com.appacoustic.rt.framework.extension.exhaustive
import com.appacoustic.rt.framework.extension.visible
import com.appacoustic.rt.framework.extension.withArgs
import kotlinx.android.synthetic.main.fragment_measure.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.androidx.scope.lifecycleScope as koinScope

class MeasureFragment : BaseFragment<
    MeasureViewModel.ViewState,
    MeasureViewModel.ViewEvents,
    MeasureViewModel
    >() {

    companion object {
        private const val ARG_RECORD_AUDIO_PERMISSION_GRANTED = "ARG_RECORD_AUDIO_PERMISSION_GRANTED"

        fun newInstance(
            navigateToPermission: () -> Unit,
            recordAudioPermissionGranted: Boolean
        ): MeasureFragment =
            MeasureFragment().withArgs {
                putBoolean(ARG_RECORD_AUDIO_PERMISSION_GRANTED, recordAudioPermissionGranted)
            }.apply {
                this.navigateToPermission = navigateToPermission
            }
    }

    private lateinit var navigateToPermission: () -> Unit

    override val layoutResId = R.layout.fragment_measure
    override val viewModel: MeasureViewModel by koinScope.viewModel(this) {
        val recordAudioPermissionGranted = requireArguments().getBoolean(ARG_RECORD_AUDIO_PERMISSION_GRANTED)
        val params = MeasureViewModel.Params(
            recordAudioPermissionGranted = recordAudioPermissionGranted
        )
        parametersOf(params)
    }

    override fun initUI() {
        initInfoButton()
    }

    private fun initInfoButton() {
        btn.setOnClickListener {
            viewModel.handleStartClicked()
        }
    }

    override fun renderViewState(viewState: MeasureViewModel.ViewState) {
        when (viewState) {
            MeasureViewModel.ViewState.Loading -> showLoading()
            MeasureViewModel.ViewState.Error -> showError()
            is MeasureViewModel.ViewState.Content -> showContent(viewState)
        }.exhaustive
    }

    private fun showLoading() {
        debugToast("Loading...")
    }

    private fun showError() {
        debugToast("Error")
    }

    private fun showContent(content: MeasureViewModel.ViewState.Content) {
        btn.setText(content.textResId)
        btn.visible()

        val measures = content.measures
        ftv125.setTime(measures[0].time.toString())
        ftv250.setTime(measures[1].time.toString())
        ftv500.setTime(measures[2].time.toString())
        ftv1000.setTime(measures[3].time.toString())
        ftv2000.setTime(measures[4].time.toString())
        ftv4000.setTime(measures[5].time.toString())
    }

    override fun handleViewEvent(viewEvent: MeasureViewModel.ViewEvents) {
        when (viewEvent) {
            MeasureViewModel.ViewEvents.ShowUI -> showUI()
            MeasureViewModel.ViewEvents.NavigateToPermission -> navigateToPermission()
        }.exhaustive
    }

    private fun showUI() {
        debugToast("Record Audio permission granted")
    }
}
