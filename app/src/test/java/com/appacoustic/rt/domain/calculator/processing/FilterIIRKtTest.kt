package com.appacoustic.rt.domain.calculator.processing

import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder2
import com.appacoustic.rt.framework.extension.roundTo10Decimals
import org.junit.Assert.assertTrue
import org.junit.Test

class FilterIIRKtTest {

    @Test
    fun `check expected values for 125 Hz`() {
        val doubles = DoubleArray(4)
        doubles[0] = 1.0
        doubles[1] = -2.0
        doubles[2] = 0.0
        doubles[3] = 4.0

        val filteredArray = doubles.filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_125)

        assertTrue(4 == filteredArray.size)
        assertTrue(0.0000392968 == filteredArray[0].roundTo10Decimals())
        assertTrue(0.0000778689 == filteredArray[1].roundTo10Decimals())
        assertTrue(-0.0000021918 == filteredArray[2].roundTo10Decimals())
        assertTrue(-0.0000022447 == filteredArray[3].roundTo10Decimals())
    }
}
