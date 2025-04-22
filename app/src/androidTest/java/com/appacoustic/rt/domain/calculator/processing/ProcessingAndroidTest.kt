package com.appacoustic.rt.domain.calculator.processing

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.appacoustic.rt.R
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder2
import com.appacoustic.rt.framework.extension.formatToExponential
import com.appacoustic.rt.framework.extension.roundTo11Decimals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProcessingAndroidTest {

    @Test
    fun check_filterIIR_returns_expected_values() {
        val filteredArray = givenFilteredArray()

        assertTrue(4 == filteredArray.size)
        assertTrue(0.00000038136 == filteredArray[0].roundTo11Decimals())
        assertTrue(0.00000244182 == filteredArray[1].roundTo11Decimals())
        assertTrue(0.00000794538 == filteredArray[2].roundTo11Decimals())
        assertTrue(0.00001808372 == filteredArray[3].roundTo11Decimals())
    }

    @Test
    fun check_schroederIntegral_returns_expected_values() {
        val filteredArray = givenFilteredArray()
        val integratedArray = filteredArray.schroederIntegral()

        assertTrue(4 == integratedArray.size)
        assertTrue("1.4544E-13" == integratedArray[0].formatToExponential())
        assertTrue("6.108E-12" == integratedArray[1].formatToExponential())
        assertTrue("6.9238E-11" == integratedArray[2].formatToExponential())
        assertTrue("3.9626E-10" == integratedArray[3].formatToExponential())
    }

    private fun givenFilteredArray(): DoubleArray {
        val context: Context = ApplicationProvider.getApplicationContext()
        val inputStream = context.resources.openRawResource(R.raw.samples)
        val xBytes = inputStream.readBytes()
        val xRaw = xBytes
            .toDoubleSamples()
            .normalize()

        val x = DoubleArray(4)
        x[0] = xRaw[22]
        x[1] = xRaw[23]
        x[2] = xRaw[24]
        x[3] = xRaw[25]
        return x.filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_125)
    }
}
