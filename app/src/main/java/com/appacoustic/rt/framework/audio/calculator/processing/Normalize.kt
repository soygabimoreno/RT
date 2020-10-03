package com.appacoustic.rt.framework.audio.calculator.processing

import kotlin.math.pow

fun DoubleArray.normalize(): DoubleArray {
    val out = DoubleArray(size)

    for (index in out.indices) {
        out[index] = this[index] / 2.0.pow(15.0)
    }

    return out
}
