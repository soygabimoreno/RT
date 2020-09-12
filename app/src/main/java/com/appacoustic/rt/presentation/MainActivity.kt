package com.appacoustic.rt.presentation

import android.content.Intent
import android.net.Uri
import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.BaseActivity
import com.appacoustic.rt.framework.extension.exhaustive
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class MainActivity : BaseActivity<MainViewModel.ViewEvents, MainViewModel>() {

    override val layoutResId = R.layout.activity_main
    override val viewModel: MainViewModel by koinScope.viewModel(this)

    override fun initUI() {
        initInfoButton()
    }

    override fun handleViewEvent(viewEvent: MainViewModel.ViewEvents) {
        when (viewEvent) {
            is MainViewModel.ViewEvents.NavigateToWeb -> navigateToWeb(viewEvent.uriString)
            MainViewModel.ViewEvents.ShowContent -> showContent()
            MainViewModel.ViewEvents.ShowRecordAudioPermissionRequiredDialog -> showRecordAudioPermissionRequiredDialog()
        }.exhaustive
    }

    private fun initInfoButton() {
        btn.setOnClickListener {
            viewModel.handleInfoClicked()
        }
    }

    private fun navigateToWeb(uriString: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
        startActivity(browserIntent)
    }

    private fun showContent() {
    }

    private fun showRecordAudioPermissionRequiredDialog() {
    }
}
