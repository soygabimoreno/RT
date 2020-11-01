package com.appacoustic.rt.presentation.player

import com.appacoustic.rt.R
import com.appacoustic.rt.framework.base.fragment.StatelessBaseFragment
import kotlinx.android.synthetic.main.fragment_player.*
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class PlayerFragment : StatelessBaseFragment<
    PlayerViewModel.ViewEvents,
    PlayerViewModel
    >() {

    companion object {
        fun newInstance() = PlayerFragment()
    }

    override val layoutResId = R.layout.fragment_player
    override val viewModel: PlayerViewModel by koinScope.viewModel(this)

    override fun initUI() {
        fabPlay1.setOnClickListener {
            viewModel.handlePlayButtonClicked(
                "white_noise_100ms",
                R.raw.white_noise_100ms
            )
        }
    }

    override fun handleViewEvent(viewEvent: PlayerViewModel.ViewEvents) {}
}
