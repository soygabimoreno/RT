package com.appacoustic.rt.framework.extension

import android.content.Context
import android.widget.Toast

fun Context.toast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
