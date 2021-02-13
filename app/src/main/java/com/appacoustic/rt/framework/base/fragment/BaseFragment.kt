package com.appacoustic.rt.framework.base.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel

abstract class BaseFragment<
    BINDING : ViewBinding,
    VIEW_STATE,
    VIEW_EVENT,
    VIEW_MODEL : BaseViewModel<VIEW_STATE, VIEW_EVENT>
    > : StatelessBaseFragment<BINDING, VIEW_EVENT, VIEW_MODEL>() {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
        viewModel.viewState.observe(
            viewLifecycleOwner,
            Observer(::renderViewState)
        )
    }

    protected abstract fun renderViewState(viewState: VIEW_STATE)
}
