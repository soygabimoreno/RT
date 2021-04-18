package com.appacoustic.rt.presentation.authentication.register

import android.view.LayoutInflater
import android.view.ViewGroup
import com.appacoustic.rt.databinding.FragmentRegisterBinding
import com.appacoustic.rt.framework.base.fragment.BaseFragment
import com.appacoustic.rt.framework.extension.debugToast
import com.appacoustic.rt.framework.extension.exhaustive
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<
    FragmentRegisterBinding,
    RegisterViewModel.ViewState,
    RegisterViewModel.ViewEvents,
    RegisterViewModel
    >() {

    companion object {
        fun newInstance(
            foo: Boolean
        ): RegisterFragment =
            RegisterFragment().apply {
                this.foo = foo
            }
    }

    private var foo = false

    override val viewBinding: (LayoutInflater, ViewGroup?) -> FragmentRegisterBinding = { layoutInflater, viewGroup ->
        FragmentRegisterBinding.inflate(
            layoutInflater,
            viewGroup,
            false
        )
    }

    override val viewModel: RegisterViewModel by viewModel()

    override fun initUI() {
    }

    override fun renderViewState(viewState: RegisterViewModel.ViewState) {
        when (viewState) {
            RegisterViewModel.ViewState.Loading -> showLoading()
            RegisterViewModel.ViewState.Error -> showError()
            is RegisterViewModel.ViewState.Content -> showContent(viewState)
        }.exhaustive
    }

    private fun showLoading() {
        debugToast("Loading...")
    }

    private fun showError() {
        debugToast("Error")
    }

    private fun showContent(content: RegisterViewModel.ViewState.Content) {
    }

    override fun handleViewEvent(viewEvent: RegisterViewModel.ViewEvents) {
        when (viewEvent) {
            RegisterViewModel.ViewEvents.Foo -> foo()
        }.exhaustive
    }

    private fun foo() {
        debugToast("foo")
    }
}
