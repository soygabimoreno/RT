package com.appacoustic.rt.domain.calculator.processing

import org.junit.Assert.assertTrue
import org.junit.Test

class NormalizeAndLinearToLogarithmicKtTest {

    @Test
    fun `check expected values`() {
        val doubles = DoubleArray(4)
        doubles[0] = 1.0
        doubles[1] = 2.0
        doubles[2] = 0.0
        doubles[3] = -3.0

        val normalizedArrayInDb = doubles.normalizeAndLinearToLogarithmic()

        assertTrue(4 == normalizedArrayInDb.size)
        assertTrue(-3.010299956639812 == normalizedArrayInDb[0])
        assertTrue(Double.NEGATIVE_INFINITY == normalizedArrayInDb[1])
        assertTrue(0.0 == normalizedArrayInDb[2])
        assertTrue(3.979400086720376 == normalizedArrayInDb[3])
    }
}
