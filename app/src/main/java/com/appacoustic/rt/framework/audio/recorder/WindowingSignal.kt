package com.appacoustic.rt.framework.audio.recorder

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

    val length = end - start + 1
    val out = DoubleArray(length)
    for (index in 0 until length) {
        out[index] = this[index + start]
    }

    return out
}
