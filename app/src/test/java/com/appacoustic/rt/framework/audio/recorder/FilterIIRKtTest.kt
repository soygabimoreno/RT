package com.appacoustic.rt.framework.audio.recorder

import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder2
import org.junit.Assert.assertTrue
import org.junit.Test

class FilterIIRKtTest {

    @Test
    fun `check expected values`() {
        val doubles = DoubleArray(5)
        doubles[0] = 1.0
        doubles[1] = 2.0
        doubles[2] = 0.0
        doubles[3] = 4.0
        doubles[4] = -7.0

        val filteredArray = doubles.filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_125)

        assertTrue(5 == filteredArray.size)
        assertTrue(3.929676515700289E-5 == filteredArray[0])
        assertTrue(2.350559373596712E-4 == filteredArray[1])
        assertTrue(6.23657812770271E-4 == filteredArray[2])
        assertTrue(0.0012406872923808388 == filteredArray[3])
        assertTrue(0.0018851381466816546 == filteredArray[4])
    }
}
