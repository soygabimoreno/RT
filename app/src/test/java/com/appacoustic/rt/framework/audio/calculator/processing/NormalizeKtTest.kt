package com.appacoustic.rt.framework.audio.calculator.processing

import org.junit.Assert.assertTrue
import org.junit.Test

class NormalizeKtTest {

    @Test
    fun `check the values of an array are normalized`() {
        val doubles = DoubleArray(4)
        doubles[0] = 16384.0
        doubles[1] = -8192.0
        doubles[2] = 3276.8
        doubles[3] = 32768.0

        val normalizedArray = doubles.normalize()

        assertTrue(0.5 == normalizedArray[0])
        assertTrue(-0.25 == normalizedArray[1])
        assertTrue(0.1 == normalizedArray[2])
        assertTrue(1.0 == normalizedArray[3])
    }
}
