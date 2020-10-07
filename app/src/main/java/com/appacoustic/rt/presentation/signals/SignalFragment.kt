package com.appacoustic.rt.presentation.signals

import com.appacoustic.rt.R
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder2
import com.appacoustic.rt.domain.calculator.processing.*
import com.appacoustic.rt.framework.audio.recorder.Recorder
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.extension.debugToast
import com.appacoustic.rt.framework.extension.exhaustive
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_signal.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class SignalFragment : BaseFragment<
    SignalViewModel.ViewState,
    SignalViewModel.ViewEvents,
    SignalViewModel
    >() {

    companion object {
        fun newInstance() = SignalFragment()
    }

    override val layoutResId = R.layout.fragment_signal
    override val viewModel: SignalViewModel by koinScope.viewModel(this)

    override fun initUI() {
        initLineChart()
        viewModel.updateContent()
    }

    private fun initLineChart() {
        // TODO
    }

    override fun renderViewState(viewState: SignalViewModel.ViewState) {
        when (viewState) {
            is SignalViewModel.ViewState.Content -> showContent(viewState)
        }.exhaustive
    }

    private fun showContent(content: SignalViewModel.ViewState.Content) {
        // ERASE
        val xBytes = content.xBytes
        if (xBytes.isNotEmpty()) {
            val x = xBytes
                .toDoubleSamples()
                .windowingSignal(300, 100)
                .toDivisibleBy32()
                .normalize()
                .filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_125)
                .muteStart(0.1, Recorder.SAMPLE_RATE)

            val entries = mutableListOf<Entry>()
            x.forEachIndexed { index, sample ->
                entries.add(Entry(index.toFloat(), sample.toFloat()))
            }

            val dataSet = LineDataSet(entries, "Foo")
            val lineData = LineData(dataSet)
            lineChart.data = lineData
            lineChart.invalidate()
        }
    }

    override fun handleViewEvent(viewEvent: SignalViewModel.ViewEvents) {
        when (viewEvent) {
            SignalViewModel.ViewEvents.Foo -> foo()
        }.exhaustive
    }

    private fun foo() {
        debugToast("foo")
    }
}
