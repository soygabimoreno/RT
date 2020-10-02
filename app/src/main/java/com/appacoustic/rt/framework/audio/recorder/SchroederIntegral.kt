package com.appacoustic.rt.framework.audio.recorder

fun DoubleArray.schroederIntegral(): DoubleArray {
    val out = DoubleArray(size)
    for (index in indices) {
        out[index] = this[index] * this[index]
    }

    var aux = out[0]
    for (index in 0 until size - 1) {
        aux += out[index]
        out[index] = aux
    }

    return out
}
