package com.appacoustic.rt.domain.calculator.processing

fun ByteArray.toDoubleSamples(): DoubleArray = mapPairsToDoubles { a, b ->
    (a.toInt() and 0xFF or (b.toInt() shl 8)).toDouble()
}

inline fun ByteArray.mapPairsToDoubles(block: (Byte, Byte) -> Double) =
    DoubleArray(size / 2) { i ->
        block(
            this[2 * i],
            this[2 * i + 1]
        )
    }
