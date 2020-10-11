package com.appacoustic.rt.domain.calculator.processing

import com.appacoustic.rt.framework.KLog
import com.appacoustic.rt.framework.extension.getSizeInMB

fun ByteArray.toDoubleSamples(): DoubleArray {
    KLog.d("xBytes size: ${getSizeInMB()} MB")
    val doubles = mapPairsToDoubles { a, b ->
        (a.toInt() and 0xFF or (b.toInt() shl 8)).toDouble()
    }
    KLog.d("x size: ${doubles.getSizeInMB()} MB")
    return doubles
}

inline fun ByteArray.mapPairsToDoubles(block: (Byte, Byte) -> Double) =
    DoubleArray(size / 2) { i ->
        block(
            this[2 * i],
            this[2 * i + 1]
        )
    }
