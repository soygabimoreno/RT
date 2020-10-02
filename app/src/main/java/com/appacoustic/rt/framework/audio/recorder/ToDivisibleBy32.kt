package com.appacoustic.rt.framework.audio.recorder

import kotlin.math.floor

fun DoubleArray.toDivisibleBy32(): DoubleArray {
    val newSize = (floor(size.toFloat() / 32) * 32).toInt()
    val out = DoubleArray(newSize)

    for (index in 0 until newSize) {
        out[index] = this[index]
    }

    return out
}
