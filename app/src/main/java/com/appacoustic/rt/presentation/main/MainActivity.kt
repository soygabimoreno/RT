package com.appacoustic.rt.presentation.main

import android.content.Intent
import android.net.Uri
import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.activity.StatelessBaseActivity
import com.appacoustic.rt.framework.extension.exhaustive
import com.appacoustic.rt.framework.extension.navigateTo
import com.appacoustic.rt.presentation.measure.MeasureFragment
import com.appacoustic.rt.presentation.permission.PermissionFragment
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class MainActivity : StatelessBaseActivity<
    MainViewModel.ViewEvents,
    MainViewModel
    >() {

    override val layoutResId = R.layout.activity_main
    override val viewModel: MainViewModel by koinScope.viewModel(this)

    override fun initUI() {
    }

    override fun handleViewEvent(viewEvent: MainViewModel.ViewEvents) {
        when (viewEvent) {
            MainViewModel.ViewEvents.NavigateToMeasure -> navigateToMeasure()
            is MainViewModel.ViewEvents.NavigateToWeb -> navigateToWeb(viewEvent.uriString)
        }.exhaustive
    }

    private fun navigateToMeasure() {
        navigateTo(
            R.id.flContainer, MeasureFragment.newInstance(
                ::navigateToPermission
            )
        )
    }

    private fun navigateToPermission() {
        navigateTo(
            R.id.flContainer, PermissionFragment.newInstance(
                ::navigateToMeasure
            )
        )
    }

    private fun navigateToWeb(uriString: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
        startActivity(browserIntent)
    }
}
