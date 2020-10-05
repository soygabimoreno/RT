package com.appacoustic.rt.domain.calculator.processing

fun DoubleArray.schroederIntegral(): DoubleArray {
    val out = DoubleArray(size)
    for (index in indices) {
        out[index] = this[index] * this[index]
    }

    var aux = out[0]
    for (index in 1 until size) {
        aux += out[index]
        out[index] = aux
    }

    return out
}
