package com.appacoustic.rt.framework.base.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.appacoustic.rt.framework.base.viewmodel.StatelessBaseViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.scope.ScopeActivity

abstract class StatelessBaseActivity<
    BINDING : ViewBinding,
    VIEW_EVENT,
    VIEW_MODEL : StatelessBaseViewModel<VIEW_EVENT>
    > : ScopeActivity() {

    abstract val viewBinding: (LayoutInflater) -> BINDING
    protected lateinit var binding: BINDING
        private set

    protected abstract val viewModel: VIEW_MODEL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = viewBinding(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            binding.root.updatePadding(
                top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            )
            insets
        }
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        initUI()
        viewModel.viewEvents
            .consumeAsFlow()
            .onEach { handleViewEvent(it) }
            .launchIn(lifecycleScope)
    }

    protected abstract fun initUI()
    protected abstract fun handleViewEvent(viewEvent: VIEW_EVENT)
}
