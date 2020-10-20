package com.appacoustic.rt.domain

import org.junit.Assert.assertTrue
import org.junit.Test

class MeasureExtensionKtTest {

    @Test
    fun `check toHumanReadable format measures correctly`() {
        val measures = listOf(
            Measure(frequency = Measure.Frequency.FREQUENCY_125, time = 0.123f),
            Measure(frequency = Measure.Frequency.FREQUENCY_250, time = 0.456f),
            Measure(frequency = Measure.Frequency.FREQUENCY_500, time = 0.789f),
            Measure(frequency = Measure.Frequency.FREQUENCY_1000, time = 0.012f),
            Measure(frequency = Measure.Frequency.FREQUENCY_2000, time = 0.345f),
            Measure(frequency = Measure.Frequency.FREQUENCY_4000, time = 0.678f),
        )

        val res = measures.toHumanReadable()

        assertTrue("[0.12, 0.46, 0.79, 0.01, 0.34, 0.68]" == res)
    }
}
