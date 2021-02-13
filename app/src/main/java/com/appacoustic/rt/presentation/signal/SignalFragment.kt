package com.appacoustic.rt.presentation.signal

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.appacoustic.rt.R
import com.appacoustic.rt.data.filter.butterworth.ButterworthFrequency
import com.appacoustic.rt.data.filter.butterworth.ButterworthOrder
import com.appacoustic.rt.databinding.FragmentSignalBinding
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.extension.disable
import com.appacoustic.rt.framework.extension.enable
import com.appacoustic.rt.framework.extension.exhaustive
import com.appacoustic.rt.framework.extension.gone
import com.appacoustic.rt.framework.extension.setOnItemSelected
import com.appacoustic.rt.framework.extension.visible
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignalFragment : BaseFragment<
    FragmentSignalBinding,
    SignalViewModel.ViewState,
    SignalViewModel.ViewEvents,
    SignalViewModel
    >() {

    companion object {
        fun newInstance() = SignalFragment()
    }

    override val viewBinding: (LayoutInflater, ViewGroup?) -> FragmentSignalBinding = { layoutInflater, viewGroup ->
        FragmentSignalBinding.inflate(
            layoutInflater,
            viewGroup,
            false
        )
    }

    override val viewModel: SignalViewModel by viewModel()

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
        initLineChart()
        initSwitchFilter()
        initSpinnerFrequency()
        initSpinnerOrder()
    }

    private fun initLineChart() {
        with(binding) {
            lineChart.setNoDataText(getString(R.string.take_a_measure_to_see_its_signal))
            lineChart.legend.isEnabled = false
            lineChart.description.isEnabled = false

            val xAxis = lineChart.xAxis
            xAxis.textColor = grayLightColor
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            val yAxis = lineChart.axisLeft
            yAxis.textColor = grayLightColor
            yAxis.axisMinimum = -1f
            yAxis.axisMaximum = 1f

            val axisRight = lineChart.axisRight
            axisRight.isEnabled = false
        }
    }

    private fun initSwitchFilter() {
        with(binding) {
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
    }

    private fun initSpinnerFrequency() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            ButterworthFrequency.values()
                .map { getString(it.stringResId) }
        )

        with(binding) {
            spFrequency.disable()
            spFrequency.adapter = adapter
            spFrequency.setOnItemSelected { position ->
                viewModel.handleSpinnerFrequencyChanged(ButterworthFrequency.values()[position])
            }
        }
    }

    private fun initSpinnerOrder() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            ButterworthOrder.values()
                .map { getString(it.stringResId) }
        )

        with(binding) {
            spOrder.disable()
            spOrder.adapter = adapter
            spOrder.setSelection(1)
            spOrder.setOnItemSelected { position ->
                viewModel.handleSpinnerOrderChanged(ButterworthOrder.values()[position])
            }
        }
    }

    override fun renderViewState(viewState: SignalViewModel.ViewState) {
        when (viewState) {
            is SignalViewModel.ViewState.Content -> showContent(viewState)
        }.exhaustive
    }

    private fun showContent(content: SignalViewModel.ViewState.Content) {
        val x = content.x
        val entries = mutableListOf<Entry>()
        x.forEachIndexed { index, sample ->
            entries.add(
                Entry(
                    index.toFloat(),
                    sample.toFloat()
                )
            )
        }

        val dataSet = LineDataSet(
            entries,
            getString(R.string.signal)
        )
        dataSet.color = accentColor
        dataSet.setCircleColor(accentColor)
        dataSet.circleRadius = 1f

        val lineData = LineData(dataSet)
        with(binding) {
            lineChart.data = lineData
            lineChart.invalidate()
        }
    }

    override fun handleViewEvent(viewEvent: SignalViewModel.ViewEvents) {
        when (viewEvent) {
            SignalViewModel.ViewEvents.ShowLoading -> showLoading()
            SignalViewModel.ViewEvents.HideLoading -> hideLoading()
        }.exhaustive
    }

    private fun showLoading() {
        binding.pb.visible()
    }

    private fun hideLoading() {
        binding.pb.gone()
    }
}
