package com.appacoustic.rt.presentation.splash

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appacoustic.rt.R
import com.appacoustic.rt.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lav?.addAnimatorListener(
            object : Animator.AnimatorListener {
                val initialFadeInDuration = 500L

                override fun onAnimationStart(animation: Animator?) {
                    lav
                        .animate()
                        .alpha(1f)
                        .setDuration(initialFadeInDuration)
                        .start()
                }

                override fun onAnimationEnd(animation: Animator?) {
                    navigateToCourses()
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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
