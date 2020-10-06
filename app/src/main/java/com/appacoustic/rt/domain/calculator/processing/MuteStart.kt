package com.appacoustic.rt.domain.calculator.processing

import kotlin.math.roundToInt

fun DoubleArray.muteStart(
    seconds: Double,
    sampleRate: Int
): DoubleArray {
    val positions = (seconds * sampleRate * 2).roundToInt()
    for (i in 0 until positions) {
        this[i] = 0.0
    }
    return this
}
