package com.appacoustic.rt.framework.audio.calculator.processing

fun DoubleArray.windowingSignal(
    backgroundNoiseThresholdAtStart: Int,
    backgroundNoiseThresholdAtTheEnd: Int
): DoubleArray {
    var start = 0
    var end = 0

    run loop@{
        for (index in indices) {
            if (this[index] >= backgroundNoiseThresholdAtStart) {
                start = index
                return@loop
            }
        }
    }

    run loop@{
        for (index in this.size - 1 downTo 0) {
            if (this[index] >= backgroundNoiseThresholdAtTheEnd) {
                end = index
                return@loop
            }
        }
    }

    val size = end - start + 1
    val out = DoubleArray(size)
    for (index in 0 until size) {
        out[index] = this[index + start]
    }

    return out
}
