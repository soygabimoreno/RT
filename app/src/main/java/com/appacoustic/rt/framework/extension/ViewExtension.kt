package com.appacoustic.rt.framework.extension

import android.view.View

fun View.setVisibleOrGone(visible: Boolean) {
    if (visible) {
        visible()
    } else {
        gone()
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.setEnableOrDisable(enable: Boolean) {
    if (enable) {
        enable()
    } else {
        disable()
    }
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}
