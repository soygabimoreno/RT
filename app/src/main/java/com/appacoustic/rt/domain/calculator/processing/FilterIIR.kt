package com.appacoustic.rt.domain.calculator.processing

import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficients

fun DoubleArray.filterIIR(
    butterworthCoefficients: ButterworthCoefficients
): DoubleArray {
    val nOrder = butterworthCoefficients.nOrder
    val bufferSize = nOrder * 2 + 1
    val buffer = DoubleArray(bufferSize)
    val out = DoubleArray(size)

    for (n in indices) {
        for (i in 0 until bufferSize - 1) {
            buffer[i] = buffer[i + 1]
        }

        buffer[bufferSize - 1] = 0.0
        for (i in 0 until bufferSize) {
            buffer[i] += this[n] * butterworthCoefficients.numeratorDenominator.b[i]
        }

        for (i in 0 until bufferSize - 1) {
            buffer[i + 1] += -buffer[0] * butterworthCoefficients.numeratorDenominator.a[i + 1]
        }
        out[n] = buffer[0]
    }

    return out
}
