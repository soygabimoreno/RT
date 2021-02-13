package com.appacoustic.rt.presentation.player

import android.view.LayoutInflater
import android.view.ViewGroup
import com.appacoustic.rt.R
import com.appacoustic.rt.databinding.FragmentPlayerBinding
import com.appacoustic.rt.framework.base.fragment.StatelessBaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerFragment : StatelessBaseFragment<
    FragmentPlayerBinding,
    PlayerViewModel.ViewEvents,
    PlayerViewModel
    >() {

    companion object {
        fun newInstance() = PlayerFragment()
    }

    override val viewBinding: (LayoutInflater, ViewGroup?) -> FragmentPlayerBinding = { layoutInflater, viewGroup ->
        FragmentPlayerBinding.inflate(
            layoutInflater,
            viewGroup,
            false
        )
    }

    override val viewModel: PlayerViewModel by viewModel()

    override fun initUI() {
        with(binding) {
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
    }

    override fun handleViewEvent(viewEvent: PlayerViewModel.ViewEvents) {}
}
