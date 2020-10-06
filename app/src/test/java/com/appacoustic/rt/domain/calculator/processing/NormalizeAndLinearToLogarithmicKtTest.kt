package com.appacoustic.rt.domain.calculator.processing

import com.appacoustic.rt.framework.extension.roundTo5Decimals
import org.junit.Assert.assertTrue
import org.junit.Test

class NormalizeAndLinearToLogarithmicKtTest {

    @Test
    fun `check expected values`() {
        val doubles = DoubleArray(4)
        doubles[0] = 1.0
        doubles[1] = -2.0
        doubles[2] = 0.0
        doubles[3] = 4.0

        val normalizedArrayInDb = doubles.normalizeAndLinearToLogarithmic()

        assertTrue(4 == normalizedArrayInDb.size)
        assertTrue(-1.24939 == normalizedArrayInDb[0].roundTo5Decimals())
        assertTrue(1.76091 == normalizedArrayInDb[1].roundTo5Decimals())
        assertTrue(0.0 == normalizedArrayInDb[2])
        assertTrue(Double.NEGATIVE_INFINITY == normalizedArrayInDb[3])
    }
}
