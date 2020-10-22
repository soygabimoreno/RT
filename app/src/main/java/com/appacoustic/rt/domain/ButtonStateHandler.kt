package com.appacoustic.rt.domain

import android.os.CountDownTimer

class ButtonStateHandler(
    private val listener: Listener
) {

    companion object {
        const val MILLIS_IN_FUTURE = 6000L
        const val COUNTDOWN_INTERVAL = 1000L
        val step1 = (MILLIS_IN_FUTURE - 0 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 1 * COUNTDOWN_INTERVAL + 1)
        val step2 = (MILLIS_IN_FUTURE - 1 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 2 * COUNTDOWN_INTERVAL + 1)
        val step3 = (MILLIS_IN_FUTURE - 2 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 3 * COUNTDOWN_INTERVAL + 1)
        val step4 = (MILLIS_IN_FUTURE - 3 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 4 * COUNTDOWN_INTERVAL + 1)
        val step5 = (MILLIS_IN_FUTURE - 4 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 5 * COUNTDOWN_INTERVAL + 1)
        val step6 = (MILLIS_IN_FUTURE - 5 * COUNTDOWN_INTERVAL) downTo (MILLIS_IN_FUTURE - 6 * COUNTDOWN_INTERVAL + 1)
    }

    enum class State {
        START,
        COUNTDOWN_3,
        COUNTDOWN_2,
        COUNTDOWN_1,
        MEASURING,
        CALCULATING,
        IDLE
    }

    interface Listener {
        fun onTick(state: State)
        fun onFinish(state: State)
        fun onReduceButtonTextSize()
        fun onAmplifyButtonTextSize()
    }

    fun start() {
        object : CountDownTimer(MILLIS_IN_FUTURE, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val state = when (millisUntilFinished) {
                    in step1 -> State.COUNTDOWN_3
                    in step2 -> State.COUNTDOWN_2
                    in step3 -> State.COUNTDOWN_1
                    in step4 -> {
                        listener.onReduceButtonTextSize()
                        State.MEASURING
                    }
                    in step5 -> State.MEASURING
                    in step6 -> State.CALCULATING
                    else -> State.IDLE
                }
                listener.onTick(state)
            }

            override fun onFinish() {
                listener.onAmplifyButtonTextSize()
                listener.onFinish(State.START)
            }
        }.start()
    }
}
