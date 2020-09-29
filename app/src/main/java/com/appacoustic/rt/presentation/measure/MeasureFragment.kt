package com.appacoustic.rt.presentation.measure

import android.util.TypedValue
import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.extension.debugToast
import com.appacoustic.rt.framework.extension.disable
import com.appacoustic.rt.framework.extension.enable
import com.appacoustic.rt.framework.extension.exhaustive
import kotlinx.android.synthetic.main.fragment_measure.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class MeasureFragment : BaseFragment<
    MeasureViewModel.ViewState,
    MeasureViewModel.ViewEvents,
    MeasureViewModel
    >() {

    companion object {
        fun newInstance(
            navigateToPermission: () -> Unit
        ): MeasureFragment =
            MeasureFragment().apply {
                this.navigateToPermission = navigateToPermission
            }
    }

    private lateinit var navigateToPermission: () -> Unit

    override val layoutResId = R.layout.fragment_measure
    override val viewModel: MeasureViewModel by koinScope.viewModel(this)

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
            MeasureViewModel.ViewEvents.EnableButton -> btn.enable()
            MeasureViewModel.ViewEvents.DisableButton -> btn.disable()
            MeasureViewModel.ViewEvents.ReduceButtonTextSize -> reduceButtonTextSize()
            MeasureViewModel.ViewEvents.AmplifyButtonTextSize -> amplifyButtonTextSize()
            MeasureViewModel.ViewEvents.NavigateToPermission -> navigateToPermission()
        }.exhaustive
    }

    private fun reduceButtonTextSize() {
        btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.textSizeSmall))
    }

    private fun amplifyButtonTextSize() {
        btn.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.textSizeBig))
    }
}
