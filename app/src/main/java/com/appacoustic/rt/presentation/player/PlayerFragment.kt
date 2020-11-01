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
        ptsvWhiteNoise100ms.setOnClickListener {
            viewModel.handlePlayButtonClicked(
                "white_noise_100ms",
                R.raw.white_noise_100ms
            )
        }
        ptsvWhiteNoise300ms.setOnClickListener {
            viewModel.handlePlayButtonClicked(
                "white_noise_300ms",
                R.raw.white_noise_300ms
            )
        }
        ptsvPinkNoise100ms.setOnClickListener {
            viewModel.handlePlayButtonClicked(
                "pink_noise_100ms",
                R.raw.pink_noise_100ms
            )
        }
        ptsvPinkNoise300ms.setOnClickListener {
            viewModel.handlePlayButtonClicked(
                "pink_noise_300ms",
                R.raw.pink_noise_300ms
            )
        }
    }

    override fun handleViewEvent(viewEvent: PlayerViewModel.ViewEvents) {}
}
