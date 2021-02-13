package com.appacoustic.rt.framework.base.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel

abstract class BaseActivity<
    BINDING : ViewBinding,
    VIEW_STATE,
    VIEW_EVENT,
    VIEW_MODEL : BaseViewModel<VIEW_STATE, VIEW_EVENT>
    > : StatelessBaseActivity<BINDING, VIEW_EVENT, VIEW_MODEL>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewState.observe(
            this,
            Observer(::renderViewState)
        )
    }

    protected abstract fun renderViewState(viewState: VIEW_STATE)
}
