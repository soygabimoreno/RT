package com.appacoustic.rt.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appacoustic.rt.R
import com.appacoustic.rt.framework.event.EventObserver
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by koinScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initInfoButton()
    }

    private fun initViewModel() {
        viewModel.navigateToWeb.observe(this, EventObserver { uriString ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
            startActivity(browserIntent)
        })
    }

    private fun initInfoButton() {
        btn.setOnClickListener {
            viewModel.handleInfoClicked()
        }
    }
}
