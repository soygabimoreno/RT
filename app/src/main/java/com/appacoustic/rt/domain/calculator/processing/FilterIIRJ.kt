package com.appacoustic.rt.domain.calculator.processing

import uk.me.berndporr.iirj.Butterworth
import kotlin.math.abs
import kotlin.math.sqrt

fun DoubleArray.filterIIRJ(
    frequency: Int,
    nOrder: Int,
    sampleRate: Int
): DoubleArray {
    val lowCutoff = frequency.toDouble() / sqrt(2.0)
    val highCutoff = frequency.toDouble() * sqrt(2.0)
    val width = abs(highCutoff - lowCutoff)
    val output = DoubleArray(size)
    val bp = Butterworth()
    bp.bandPass(
        nOrder,
        sampleRate.toDouble(),
        frequency.toDouble(),
        width
    )
    for (i in output.indices) {
        output[i] = bp.filter(this[i])
    }
    return output
}
