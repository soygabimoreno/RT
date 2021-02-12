package com.appacoustic.rt.presentation.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.forEach
import com.appacoustic.rt.BuildConfig
import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.activity.StatelessBaseActivity
import com.appacoustic.rt.framework.extension.exhaustive
import com.appacoustic.rt.framework.extension.navigateTo
import com.appacoustic.rt.presentation.measure.MeasureFragment
import com.appacoustic.rt.presentation.permission.PermissionFragment
import com.appacoustic.rt.presentation.player.PlayerFragment
import com.appacoustic.rt.presentation.settings.SettingsFragment
import com.appacoustic.rt.presentation.signal.SignalFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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
    override val viewModel: MainViewModel by viewModel()

    private lateinit var menu: Menu

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu!!
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
            is MainViewModel.ViewEvents.NavigateToMeasure -> navigateToMeasure(viewEvent.updateContent)
            MainViewModel.ViewEvents.NavigateToSignal -> navigateToSignal()
            MainViewModel.ViewEvents.NavigateToPlayer -> navigateToPlayer()
            MainViewModel.ViewEvents.NavigateToSettings -> navigateToSettings()
            MainViewModel.ViewEvents.Share -> share()
            MainViewModel.ViewEvents.SendEmail -> sendEmail()
            MainViewModel.ViewEvents.ExternalRate -> externalRate()
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

    private fun navigateToPlayer() {
        navigateTo(
            R.id.flContainer,
            PlayerFragment.newInstance()
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

    private fun externalRate() {
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
        menu.forEach { menuItem ->
            menuItem.isEnabled = enable
        }
        bnv.menu.forEach { menuItem ->
            menuItem.isEnabled = enable
        }
    }

    private fun initBottomNavigation() {
        bnv.menu.getItem(1).isVisible = false
        bnv.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bnmMeasures -> {
                    viewModel.handleBottomNavigationMenuMeasureClicked()
                    true
                }
                R.id.bnmSignal -> {
                    viewModel.handleBottomNavigationMenuSignalClicked()
                    true
                }
                R.id.bnmPlayer -> {
                    viewModel.handleBottomNavigationMenuPlayerClicked()
                    true
                }
                R.id.bnmSettings -> {
                    viewModel.handleBottomNavigationMenuSettingsClicked()
                    true
                }
                else -> false
            }
        }
        bnv.setOnNavigationItemReselectedListener { }
    }
}
