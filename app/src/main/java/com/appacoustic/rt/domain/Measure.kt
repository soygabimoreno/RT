package com.appacoustic.rt.domain

data class Measure(
    val frequency: Frequency,
    val time: Float
) {

    enum class Frequency(val frequency: Int) {
        FREQUENCY_125(125),
        FREQUENCY_250(250),
        FREQUENCY_500(500),
        FREQUENCY_1000(1000),
        FREQUENCY_2000(2000),
        FREQUENCY_4000(4000)
    }
}
