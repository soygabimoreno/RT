package com.appacoustic.rt.framework.audio.recorder

import com.appacoustic.rt.framework.KLog
import kotlin.math.roundToInt

fun muteStartAndEnd(
    bytes: ByteArray,
    seconds: Double,
    sampleRate: Int
): ByteArray {
    val positions = (seconds * sampleRate * 2).roundToInt()

    for (i in 0 until positions) {
        bytes[i] = 0
    }

    val size = bytes.size
    for (i in (size - positions) until size) {
        bytes[i] = 0
    }

    KLog.d("bytes: $bytes")
    return bytes
}
