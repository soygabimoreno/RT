package com.appacoustic.rt.framework.audio.calculator.processing

import kotlin.math.log10

fun DoubleArray.normalizeAndLinearToLogarithmic(): DoubleArray {
    val out = DoubleArray(size)
    var max = this[0]

    for (index in 0 until size - 1) {
        val nextInputValue = this[index + 1]
        if (nextInputValue > max) {
            max = nextInputValue
        }
    }

    for (index in indices) {
        out[index] = 10 * log10(1 - this[index] / max)
    }

    return out
}
