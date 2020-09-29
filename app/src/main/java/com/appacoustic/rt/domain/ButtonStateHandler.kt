package com.appacoustic.rt.domain

import android.os.CountDownTimer
import androidx.annotation.StringRes
import com.appacoustic.rt.R

class ButtonStateHandler(
    private val listener: Listener
) {

    companion object {
        const val MILLIS_IN_FUTURE = 7000L
        const val COUNTDOWN_INTERVAL = 1000L
        val step1 = (MILLIS_IN_FUTURE - 0 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 1 * COUNTDOWN_INTERVAL + 1)
        val step2 = (MILLIS_IN_FUTURE - 1 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 2 * COUNTDOWN_INTERVAL + 1)
        val step3 = (MILLIS_IN_FUTURE - 2 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 3 * COUNTDOWN_INTERVAL + 1)
        val step4 = (MILLIS_IN_FUTURE - 3 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 4 * COUNTDOWN_INTERVAL + 1)
        val step5 = (MILLIS_IN_FUTURE - 4 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 5 * COUNTDOWN_INTERVAL + 1)
    }

    interface Listener {
        fun onTick(@StringRes textResId: Int)
        fun onFinish(@StringRes textResId: Int)
        fun onReduceButtonTextSize()
        fun onAmplifyButtonTextSize()
    }

    fun start() {
        object : CountDownTimer(MILLIS_IN_FUTURE, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val textResId = when (millisUntilFinished) {
                    in step1 -> R.string.countdown_3
                    in step2 -> R.string.countdown_2
                    in step3 -> R.string.countdown_1
                    in step4 -> {
                        listener.onReduceButtonTextSize()
                        R.string.measuring
                    }
                    in step5 -> R.string.calculating
                    else -> R.string.undefined
                }
                listener.onTick(textResId)
            }

            override fun onFinish() {
                listener.onAmplifyButtonTextSize()
                listener.onFinish(R.string.start)
            }
        }.start()
    }
}
