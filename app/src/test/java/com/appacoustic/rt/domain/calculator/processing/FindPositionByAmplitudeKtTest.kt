package com.appacoustic.rt.domain.calculator.processing

import org.junit.Assert.assertTrue
import org.junit.Test

class FindPositionByAmplitudeKtTest {

    @Test
    fun `check expected values`() {
        val doubles = DoubleArray(4)
        doubles[0] = 1.0
        doubles[1] = -2.0
        doubles[2] = 0.0
        doubles[3] = 4.0

        val startPosition = doubles.findPositionByAmplitude(-5)
        val endPosition = doubles.findPositionByAmplitude(-15)

        assertTrue(2 == startPosition)
        assertTrue(2 == endPosition)
    }
}
