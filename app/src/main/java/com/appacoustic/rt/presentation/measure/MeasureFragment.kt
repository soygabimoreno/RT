package com.appacoustic.rt.presentation.measure

import android.util.TypedValue
import com.appacoustic.rt.R
import com.appacoustic.rt.domain.Measure
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.customview.FrequencyTimeView
import com.appacoustic.rt.framework.extension.*
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

    override val layoutResId = R.layout.fragment_measure
    override val viewModel: MeasureViewModel by koinScope.viewModel(this)

    private val ftvs by lazy {
        listOf(
            ftv125,
            ftv250,
            ftv500,
            ftv1000,
            ftv2000,
            ftv4000
        )
    }

    override fun initUI() {
        initButton()
        if (updateContent) {
            viewModel.updateContent()
        }
    }

    private fun initButton() {
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
        val textResId = content.textResId
        btn.setText(textResId)

        if (textResId == R.string.start) {
            val measures = content.measures
            ftvs.setTime(measures)
            val averageReverbTime = content.averageReverbTime
            tvAverage.text = getString(
                R.string.average_x_s,
                averageReverbTime.formatTo2Decimals()
            )
        } else {
            ftvs.setUndefinedTime()
            tvAverage.text = getString(
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
        }.exhaustive
    }

    private fun showEmptySignalError() {
        toast(R.string.error_empty_signal)
    }

    private fun enableScreenPerform(enable: Boolean) {
        enableScreen(enable)
        btn.setEnableOrDisable(enable)
    }

    private fun reduceButtonTextSize() {
        btn.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimension(R.dimen.textSizeXXXS)
        )
    }

    private fun amplifyButtonTextSize() {
        btn.setTextSize(
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
}
