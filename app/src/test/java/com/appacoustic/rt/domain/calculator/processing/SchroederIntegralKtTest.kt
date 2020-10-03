package com.appacoustic.rt.domain.calculator.processing

import org.junit.Assert.assertTrue
import org.junit.Test

class SchroederIntegralKtTest {

    @Test
    fun `check expected values`() {
        val doubles = DoubleArray(5)
        doubles[0] = 1.0
        doubles[1] = 2.0
        doubles[2] = 0.0
        doubles[3] = 4.0
        doubles[4] = -7.0

        val integratedArray = doubles.schroederIntegral()

        assertTrue(5 == integratedArray.size)
        assertTrue(2.0 == integratedArray[0])
        assertTrue(6.0 == integratedArray[1])
        assertTrue(6.0 == integratedArray[2])
        assertTrue(22.0 == integratedArray[3])
        assertTrue(49.0 == integratedArray[4])
    }
}
