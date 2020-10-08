package com.appacoustic.rt.presentation.main

import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.browser.customtabs.CustomTabsIntent
import com.appacoustic.rt.BuildConfig
import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.activity.StatelessBaseActivity
import com.appacoustic.rt.framework.extension.exhaustive
import com.appacoustic.rt.framework.extension.navigateTo
import com.appacoustic.rt.presentation.measure.MeasureFragment
import com.appacoustic.rt.presentation.permission.PermissionFragment
import com.appacoustic.rt.presentation.signals.SignalFragment
import kotlinx.android.synthetic.main.activity_main.*
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
            R.id.menuShare -> {
                viewModel.handleShareClicked()
                true
            }
            R.id.menuEmail -> {
                viewModel.handleEmailClicked()
                true
            }
            R.id.menuRate -> {
                viewModel.handleRateClicked()
                true
            }
            R.id.menuInfo -> {
                viewModel.handleInfoClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initUI() {
        initBottomNavigation()
    }

    override fun handleViewEvent(viewEvent: MainViewModel.ViewEvents) {
        when (viewEvent) {
            is MainViewModel.ViewEvents.NavigateToMeasure -> navigateToMeasure()
            MainViewModel.ViewEvents.NavigateToSignal -> navigateToSignal()
            MainViewModel.ViewEvents.Share -> share()
            MainViewModel.ViewEvents.SendEmail -> sendEmail()
            MainViewModel.ViewEvents.Rate -> rate()
            is MainViewModel.ViewEvents.NavigateToWeb -> navigateToWeb(viewEvent.uriString)
        }.exhaustive
    }

    private fun navigateToMeasure() {
        navigateTo(
            R.id.flContainer,
            MeasureFragment.newInstance(
                navigateToPermission = ::navigateToPermission
            )
        )
    }

    private fun navigateToSignal() {
        navigateTo(
            R.id.flContainer,
            SignalFragment.newInstance()
        )
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_title))
        startActivity(shareIntent)
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_to)))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))
        startActivity(Intent.createChooser(intent, getString(R.string.email_title)))
    }

    private fun rate() {
        val appPackageName = if (BuildConfig.DEBUG) "com.appacoustic.rt" else packageName
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (exception: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
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

    private fun initBottomNavigation() {
        bnv.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnmMeasures -> {
                    viewModel.handleBottomNavigationMenuMeasureClicked()
                    true
                }
                R.id.bnmSignals -> {
                    viewModel.handleBottomNavigationMenuSignalClicked()
                    true
                }
                else -> false
            }
        }
        bnv.setOnNavigationItemReselectedListener { }
    }
}
