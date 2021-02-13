package com.appacoustic.rt.framework.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.appacoustic.rt.framework.base.viewmodel.StatelessBaseViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.scope.ScopeFragment

abstract class StatelessBaseFragment<
    BINDING : ViewBinding,
    VIEW_EVENT,
    VIEW_MODEL : StatelessBaseViewModel<VIEW_EVENT>
    > : ScopeFragment() {

    abstract val viewBinding: (LayoutInflater, ViewGroup?) -> BINDING
    protected lateinit var binding: BINDING

    protected abstract val viewModel: VIEW_MODEL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(
            inflater,
            container
        )
        return binding.root
    }

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
