package com.appacoustic.rt.framework.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.lifecycleScope
import com.appacoustic.rt.framework.base.viewmodel.StatelessBaseViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.scope.ScopeFragment

abstract class StatelessBaseFragment<
    VIEW_EVENT,
    VIEW_MODEL : StatelessBaseViewModel<VIEW_EVENT>
    > : ScopeFragment() {

    @get:LayoutRes
    protected abstract val layoutResId: Int
    protected abstract val viewModel: VIEW_MODEL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        layoutResId,
        container,
        false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewEvents
            .consumeAsFlow()
            .onEach { handleViewEvent(it) }
            .launchIn(lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    protected abstract fun initUI()
    protected abstract fun handleViewEvent(viewEvent: VIEW_EVENT)
}
