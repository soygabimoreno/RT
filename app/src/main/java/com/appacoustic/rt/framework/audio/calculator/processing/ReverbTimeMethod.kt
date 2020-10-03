package com.appacoustic.rt.framework.audio.calculator.processing

enum class ReverbTimeMethod(
    val dBStart: Int,
    val dBEnd: Int
) {
    EDT(
        dBStart = -5,
        dBEnd = -15
    )
}
