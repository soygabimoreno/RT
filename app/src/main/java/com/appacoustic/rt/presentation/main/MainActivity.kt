package com.appacoustic.rt.presentation.main

import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.browser.customtabs.CustomTabsIntent
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuInfo -> {
                viewModel.handleInfoClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initUI() {}

    override fun handleViewEvent(viewEvent: MainViewModel.ViewEvents) {
        when (viewEvent) {
            is MainViewModel.ViewEvents.NavigateToMeasure -> navigateToMeasure(viewEvent.recordAudioPermissionGranted)
            is MainViewModel.ViewEvents.NavigateToWeb -> navigateToWeb(viewEvent.uriString)
        }.exhaustive
    }

    private fun navigateToMeasure(recordAudioPermissionGranted: Boolean) {
        navigateTo(
            R.id.flContainer, MeasureFragment.newInstance(
                navigateToPermission = ::navigateToPermission,
                recordAudioPermissionGranted = recordAudioPermissionGranted
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
        CustomTabsIntent.Builder()
            .setStartAnimations(
                this,
                R.anim.browser_in_right,
                R.anim.browser_out_left
            )
            .setExitAnimations(
                this,
                R.anim.browser_in_left,
                R.anim.browser_out_right
            )
            .build()
            .launchUrl(
                this,
                Uri.parse(uriString)
            )
    }
}
