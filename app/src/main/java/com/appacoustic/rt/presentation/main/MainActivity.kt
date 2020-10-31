package com.appacoustic.rt.presentation.main

import android.content.Context
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
import com.appacoustic.rt.framework.extension.setVisibleOrGone
import com.appacoustic.rt.presentation.measure.MeasureFragment
import com.appacoustic.rt.presentation.permission.PermissionFragment
import com.appacoustic.rt.presentation.settings.SettingsFragment
import com.appacoustic.rt.presentation.signal.SignalFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class MainActivity : StatelessBaseActivity<
    MainViewModel.ViewEvents,
    MainViewModel
    >() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(
                context,
                MainActivity::class.java
            )
            context.startActivity(intent)
        }
    }

    override val layoutResId = R.layout.activity_main
    override val viewModel: MainViewModel by koinScope.viewModel(this)

    private var areButtonsAvailable = true

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(
            R.menu.menu_main,
            menu
        )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuShare -> {
                if (areButtonsAvailable) {
                    viewModel.handleShareClicked()
                }
                true
            }
            R.id.menuEmail -> {
                if (areButtonsAvailable) {
                    viewModel.handleEmailClicked()
                }
                true
            }
            R.id.menuRate -> {
                if (areButtonsAvailable) {
                    viewModel.handleRateClicked()
                }
                true
            }
            R.id.menuInfo -> {
                if (areButtonsAvailable) {
                    viewModel.handleInfoClicked()
                }
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
            is MainViewModel.ViewEvents.NavigateToMeasure -> navigateToMeasure(viewEvent.updateContent)
            MainViewModel.ViewEvents.NavigateToSignal -> navigateToSignal()
            MainViewModel.ViewEvents.NavigateToSettings -> navigateToSettings()
            MainViewModel.ViewEvents.Share -> share()
            MainViewModel.ViewEvents.SendEmail -> sendEmail()
            MainViewModel.ViewEvents.Rate -> rate()
            is MainViewModel.ViewEvents.NavigateToWeb -> navigateToWeb(viewEvent.uriString)
        }.exhaustive
    }

    private fun navigateToMeasure(updateContent: Boolean) {
        navigateTo(
            R.id.flContainer,
            MeasureFragment.newInstance(
                updateContent = updateContent,
                navigateToPermission = ::navigateToPermission,
                enableScreen = ::enableScreen
            )
        )
    }

    private fun navigateToSignal() {
        navigateTo(
            R.id.flContainer,
            SignalFragment.newInstance()
        )
    }

    private fun navigateToSettings() {
        navigateTo(
            R.id.flContainer,
            SettingsFragment.newInstance()
        )
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_text)
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(
            sendIntent,
            getString(R.string.share_title)
        )
        startActivity(shareIntent)
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(
            Intent.EXTRA_EMAIL,
            arrayOf(getString(R.string.email_to))
        )
        intent.putExtra(
            Intent.EXTRA_SUBJECT,
            getString(R.string.email_subject)
        )
        startActivity(
            Intent.createChooser(
                intent,
                getString(R.string.email_title)
            )
        )
    }

    private fun rate() {
        val appPackageName = if (BuildConfig.DEBUG) "com.appacoustic.rt" else packageName
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (exception: Exception) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    private fun navigateToPermission() {
        navigateTo(
            R.id.flContainer,
            PermissionFragment.newInstance(
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

    private fun enableScreen(enable: Boolean) {
        this.areButtonsAvailable = enable
        vOverlay.setVisibleOrGone(!enable)
    }

    private fun initBottomNavigation() {
        bnv.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnmMeasures -> {
                    if (areButtonsAvailable) {
                        viewModel.handleBottomNavigationMenuMeasureClicked()
                    }
                    true
                }
                R.id.bnmSignal -> {
                    if (areButtonsAvailable) {
                        viewModel.handleBottomNavigationMenuSignalClicked()
                    }
                    true
                }
                R.id.bnmSettings -> {
                    if (areButtonsAvailable) {
                        viewModel.handleBottomNavigationMenuSettingsClicked()
                    }
                    true
                }
                else -> false
            }
        }
        bnv.setOnNavigationItemReselectedListener { }
    }
}
