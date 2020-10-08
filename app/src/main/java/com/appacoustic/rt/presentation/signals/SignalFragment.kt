package com.appacoustic.rt.presentation.signals

import android.widget.ArrayAdapter
import com.appacoustic.rt.R
import com.appacoustic.rt.data.filter.butterworth.ButterworthFrequency
import com.appacoustic.rt.data.filter.butterworth.ButterworthOrder
import com.appacoustic.rt.domain.calculator.processing.*
import com.appacoustic.rt.framework.audio.recorder.Recorder
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.extension.*
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
        initSwitchFilter()
        initSpinnerFrequency()
        initSpinnerOrder()
        viewModel.updateContent()
    }

    private fun initLineChart() {
        // TODO
    }

    private fun initSwitchFilter() {
        switchFilter.setOnCheckedChangeListener { _, filterEnabled ->
            if (filterEnabled) {
                switchFilter.setText(R.string.filter_enabled)
                spFrequency.enable()
                spOrder.enable()
            } else {
                switchFilter.setText(R.string.filter_disabled)
                spFrequency.disable()
                spOrder.disable()
            }
            viewModel.handleSwitchFilterChanged(filterEnabled)
        }
    }

    private fun initSpinnerFrequency() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            ButterworthFrequency.values().map { getString(it.stringResId) }
        )

        spFrequency.disable()
        spFrequency.adapter = adapter
        spFrequency.setOnItemSelected { position ->
            viewModel.handleSpinnerFrequencyChanged(ButterworthFrequency.values()[position])
        }
    }

    private fun initSpinnerOrder() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            ButterworthOrder.values().map { getString(it.stringResId) }
        )

        spOrder.disable()
        spOrder.adapter = adapter
        spOrder.setSelection(1)
        spOrder.setOnItemSelected { position ->
            viewModel.handleSpinnerOrderChanged(ButterworthOrder.values()[position])
        }
    }

    override fun renderViewState(viewState: SignalViewModel.ViewState) {
        when (viewState) {
            is SignalViewModel.ViewState.Content -> showContent(viewState)
        }.exhaustive
    }

    private fun showContent(content: SignalViewModel.ViewState.Content) {
        val xBytes = content.xBytes
        val butterworthCoefficients = content.butterworthCoefficients
        if (xBytes.isNotEmpty()) {
            val filterEnabled = content.filterEnabled
            val x = if (filterEnabled) {
                xBytes
                    .toDoubleSamples()
                    .windowingSignal(300, 100)
                    .toDivisibleBy32()
                    .normalize()
                    .filterIIR(butterworthCoefficients)
                    .muteStart(0.1, Recorder.SAMPLE_RATE)
            } else {
                xBytes
                    .toDoubleSamples()
                    .windowingSignal(300, 100)
                    .toDivisibleBy32()
                    .normalize()
            }

            val entries = mutableListOf<Entry>()
            x.forEachIndexed { index, sample ->
                entries.add(Entry(index.toFloat(), sample.toFloat()))
            }

            val dataSet = LineDataSet(
                entries,
                getString(R.string.signal)
            )
            dataSet.circleRadius = 1f
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
