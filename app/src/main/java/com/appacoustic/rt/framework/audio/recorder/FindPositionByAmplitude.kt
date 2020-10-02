package com.appacoustic.rt.framework.audio.recorder

import kotlin.math.abs

fun DoubleArray.findPositionByAmplitude(amplitudeInDb: Int): Int {

    // FIXME: This is surely bad calculated

    val aux = DoubleArray(size)

    for (index in indices) {
        aux[index] = abs(this[index]) - amplitudeInDb
    }

    var place = aux[0]
    var position = -1
    for (index in 0 until aux.size - 1) {
        val nextValue = aux[index + 1]
        if (nextValue < place) {
            place = nextValue
            position = index
        }
    }

    return position + 2
}
