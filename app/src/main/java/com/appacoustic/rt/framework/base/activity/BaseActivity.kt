package com.appacoustic.rt.framework.base.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import com.appacoustic.rt.framework.base.viewmodel.BaseViewModel

abstract class BaseActivity<
    VIEW_STATE,
    VIEW_EVENT,
    VIEW_MODEL : BaseViewModel<VIEW_STATE, VIEW_EVENT>
    > : StatelessBaseActivity<VIEW_EVENT, VIEW_MODEL>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewState.observe(this, Observer(::renderViewState))
    }

    protected abstract fun renderViewState(viewState: VIEW_STATE)
}
