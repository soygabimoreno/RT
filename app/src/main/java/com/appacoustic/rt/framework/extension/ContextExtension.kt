package com.appacoustic.rt.framework.extension

import android.content.Context
import android.widget.Toast
import com.appacoustic.rt.BuildConfig

fun Context.toast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.debugToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    if (BuildConfig.DEBUG) Toast.makeText(this, message, duration).show()
}
