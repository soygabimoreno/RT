package com.appacoustic.rt.framework.extension

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.round

fun Double.roundTo9Decimals(): Double {
    val decimals = 9
    return roundToDecimals(decimals)
}

fun Double.roundTo10Decimals(): Double {
    val decimals = 10
    return roundToDecimals(decimals)
}

fun Double.roundTo11Decimals(): Double {
    val decimals = 11
    return roundToDecimals(decimals)
}

fun Double.roundToDecimals(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun Double.formatToExponential(): String {
    val df = DecimalFormat("0.####E0")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this)
}
