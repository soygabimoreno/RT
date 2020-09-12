package com.appacoustic.rt.framework.extension

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.appacoustic.rt.BuildConfig

fun Fragment.toast(message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun Fragment.debugToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    if (BuildConfig.DEBUG) Toast.makeText(requireContext(), message, duration).show()
}
