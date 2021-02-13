package com.appacoustic.rt.presentation.splash

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appacoustic.rt.R
import com.appacoustic.rt.databinding.ActivitySplashBinding
import com.appacoustic.rt.presentation.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lav.addAnimatorListener(
            object : Animator.AnimatorListener {
                val initialFadeInDuration = 500L

                override fun onAnimationStart(animation: Animator?) {
                    binding.lav
                        .animate()
                        .alpha(1f)
                        .setDuration(initialFadeInDuration)
                        .start()
                }

                override fun onAnimationEnd(animation: Animator?) {
                    navigateToCourses()
                    overridePendingTransition(
                        R.anim.fade_in,
                        R.anim.fade_out
                    )
                }

                override fun onAnimationRepeat(animation: Animator?) {}

                override fun onAnimationCancel(animation: Animator?) {}
            })
    }

    private fun navigateToCourses() {
        MainActivity.launch(this)
        finish()
    }
}
