package com.appacoustic.rt.framework.audio.recorder

enum class ReverbTimeMethod(
    val dBStart: Int,
    val dBEnd: Int
) {
    EDT(
        dBStart = -5,
        dBEnd = -15
    )
}
