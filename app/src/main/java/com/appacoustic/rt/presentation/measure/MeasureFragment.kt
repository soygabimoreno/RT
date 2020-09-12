package com.appacoustic.rt.presentation.measure

import android.content.Intent
import android.net.Uri
import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.extension.debugToast
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
            viewModel.handleStartMeasureClicked()
        }
    }

    override fun renderViewState(viewState: MeasureViewModel.ViewState) {
        when (viewState) {
            MeasureViewModel.ViewState.Loading -> showLoading()
            MeasureViewModel.ViewState.Error -> showError()
            is MeasureViewModel.ViewState.Content -> showContent(viewState.text)
        }.exhaustive
    }

    private fun showLoading() {
        debugToast("Loading...")
    }

    private fun showError() {
        debugToast("Error")
    }

    private fun showContent(text: String) {
        btn.text = text

        // TODO: Replace a fragment
    }

    override fun handleViewEvent(viewEvent: MeasureViewModel.ViewEvents) {
        when (viewEvent) {
            MeasureViewModel.ViewEvents.ShowUI -> showUI()
            MeasureViewModel.ViewEvents.NavigateToPermission -> navigateToPermission()
            is MeasureViewModel.ViewEvents.NavigateToWeb -> navigateToWeb(viewEvent.uriString)
        }.exhaustive
    }

    private fun navigateToWeb(uriString: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
        startActivity(browserIntent)
    }

    private fun showUI() {
        debugToast("showUI")
    }
}
