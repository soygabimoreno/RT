package com.appacoustic.rt.domain.calculator.processing

import org.junit.Assert.assertTrue
import org.junit.Test

class SchroederIntegralKtTest {

    @Test
    fun `check expected values`() {
        val doubles = DoubleArray(4)
        doubles[0] = 1.0
        doubles[1] = -2.0
        doubles[2] = 0.0
        doubles[3] = 4.0

        val integratedArray = doubles.schroederIntegral()

        assertTrue(4 == integratedArray.size)
        assertTrue(1.0 == integratedArray[0])
        assertTrue(5.0 == integratedArray[1])
        assertTrue(5.0 == integratedArray[2])
        assertTrue(21.0 == integratedArray[3])
    }
}
