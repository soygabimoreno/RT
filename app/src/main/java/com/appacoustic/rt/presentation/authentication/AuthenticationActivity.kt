package com.appacoustic.rt.presentation.authentication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.appacoustic.rt.databinding.ActivityAuthenticationBinding
import com.appacoustic.rt.framework.base.activity.StatelessBaseActivity
import com.appacoustic.rt.framework.extension.exhaustive
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthenticationActivity : StatelessBaseActivity<
    ActivityAuthenticationBinding,
    AuthenticationViewModel.ViewEvents,
    AuthenticationViewModel
    >() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(
                context,
                AuthenticationActivity::class.java
            )
            context.startActivity(intent)
        }
    }

    override val viewBinding: (LayoutInflater) -> ActivityAuthenticationBinding = {
        ActivityAuthenticationBinding.inflate(it)
    }

    override val viewModel: AuthenticationViewModel by viewModel()

    override fun initUI() {
    }

    override fun handleViewEvent(viewEvent: AuthenticationViewModel.ViewEvents) {
        when (viewEvent) {
            is AuthenticationViewModel.ViewEvents.Foo -> foo()
        }.exhaustive
    }

    private fun foo() {
    }
}
