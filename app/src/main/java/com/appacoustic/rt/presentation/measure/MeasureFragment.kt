package com.appacoustic.rt.presentation.measure

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import com.appacoustic.rt.R
import com.appacoustic.rt.databinding.FragmentMeasureBinding
import com.appacoustic.rt.domain.Measure
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.customview.FrequencyTimeView
import com.appacoustic.rt.framework.extension.debugToast
import com.appacoustic.rt.framework.extension.exhaustive
import com.appacoustic.rt.framework.extension.formatTo2Decimals
import com.appacoustic.rt.framework.extension.setEnableOrDisable
import com.appacoustic.rt.framework.extension.toast
import com.appacoustic.rt.framework.showinappreview.ShowInAppReview
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeasureFragment : BaseFragment<
    FragmentMeasureBinding,
    MeasureViewModel.ViewState,
    MeasureViewModel.ViewEvents,
    MeasureViewModel
    >() {

    companion object {
        fun newInstance(
            updateContent: Boolean,
            navigateToPermission: () -> Unit,
            enableScreen: (enable: Boolean) -> Unit
        ): MeasureFragment =
            MeasureFragment().apply {
                this.updateContent = updateContent
                this.navigateToPermission = navigateToPermission
                this.enableScreen = enableScreen
            }
    }

    private var updateContent = false
    private lateinit var navigateToPermission: () -> Unit
    private lateinit var enableScreen: (enable: Boolean) -> Unit

    override val viewBinding: (LayoutInflater, ViewGroup?) -> FragmentMeasureBinding = { layoutInflater, viewGroup ->
        FragmentMeasureBinding.inflate(
            layoutInflater,
            viewGroup,
            false
        )
    }

    override val viewModel: MeasureViewModel by viewModel()

    private val ftvs by lazy {
        with(binding) {
            listOf(
                ftv125,
                ftv250,
                ftv500,
                ftv1000,
                ftv2000,
                ftv4000
            )
        }
    }

    override fun initUI() {
        initButton()
        if (updateContent) {
            viewModel.updateContent()
        }
    }

    private fun initButton() {
        binding.btn.setOnClickListener {
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
        val textResId = content.textResId
        binding.btn.setText(textResId)
        if (textResId == R.string.start) {
            val measures = content.measures
            ftvs.setTime(measures)
            val averageReverbTime = content.averageReverbTime
            binding.tvAverage.text = getString(
                R.string.average_x_s,
                averageReverbTime.formatTo2Decimals()
            )
        } else {
            ftvs.setUndefinedTime()
            binding.tvAverage.text = getString(
                R.string.average_x_s,
                "?"
            )
        }
    }

    override fun handleViewEvent(viewEvent: MeasureViewModel.ViewEvents) {
        when (viewEvent) {
            MeasureViewModel.ViewEvents.EmptySignalError -> showEmptySignalError()
            is MeasureViewModel.ViewEvents.EnableScreen -> enableScreenPerform(viewEvent.enable)
            MeasureViewModel.ViewEvents.ReduceButtonTextSize -> reduceButtonTextSize()
            MeasureViewModel.ViewEvents.AmplifyButtonTextSize -> amplifyButtonTextSize()
            MeasureViewModel.ViewEvents.NavigateToPermission -> navigateToPermission()
            MeasureViewModel.ViewEvents.ShowInAppReview -> showInAppReview()
        }.exhaustive
    }

    private fun showEmptySignalError() {
        toast(R.string.error_empty_signal)
    }

    private fun enableScreenPerform(enable: Boolean) {
        enableScreen(enable)
        binding.btn.setEnableOrDisable(enable)
    }

    private fun reduceButtonTextSize() {
        binding.btn.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.textSizeXXXS)
        )
    }

    private fun amplifyButtonTextSize() {
        binding.btn.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.textSizeL)
        )
    }

    private fun List<FrequencyTimeView>.setTime(measures: List<Measure>) =
        forEachIndexed { index, ftv ->
            val time = measures[index].time
            if (time < 2) {
                ftv.setDefaultColor()
            } else {
                ftv.setErrorColor()
            }
            ftv.setTime(time.formatTo2Decimals())
        }

    private fun List<FrequencyTimeView>.setUndefinedTime() =
        forEach { ftv ->
            ftv.setTime("?")
            ftv.setDefaultColor()
        }

    private fun showInAppReview() {
        val showInAppReview: ShowInAppReview by inject()
        showInAppReview(requireActivity())
    }
}
