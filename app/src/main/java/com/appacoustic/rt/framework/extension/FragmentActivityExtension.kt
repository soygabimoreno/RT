package com.appacoustic.rt.framework.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.navigateTo(containerResId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(containerResId, fragment)
        .commit()
}

fun FragmentActivity.navigateAddingToBackStackTo(containerResId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(containerResId, fragment)
        .addToBackStack(fragment::class.java.simpleName)
        .commit()
}
