package com.appacoustic.rt.framework.mapper

import com.appacoustic.rt.R
import com.appacoustic.rt.domain.ButtonStateHandler

fun ButtonStateHandler.State.toStringResId() = when (this) {
    ButtonStateHandler.State.START -> R.string.start
    ButtonStateHandler.State.COUNTDOWN_3 -> R.string.countdown_3
    ButtonStateHandler.State.COUNTDOWN_2 -> R.string.countdown_2
    ButtonStateHandler.State.COUNTDOWN_1 -> R.string.countdown_1
    ButtonStateHandler.State.MEASURING -> R.string.measuring
    ButtonStateHandler.State.CALCULATING -> R.string.calculating
    ButtonStateHandler.State.IDLE -> R.string.undefined
}
