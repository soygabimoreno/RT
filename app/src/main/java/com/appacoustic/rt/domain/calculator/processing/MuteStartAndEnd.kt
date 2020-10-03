package com.appacoustic.rt.domain.calculator.processing

import kotlin.math.roundToInt

fun ByteArray.muteStartAndEnd(
    seconds: Double,
    sampleRate: Int
): ByteArray {
    val positions = (seconds * sampleRate * 2).roundToInt()

    for (i in 0 until positions) {
        this[i] = 0
    }

    val size = size
    for (i in (size - positions) until size) {
        this[i] = 0
    }

    return this
}
