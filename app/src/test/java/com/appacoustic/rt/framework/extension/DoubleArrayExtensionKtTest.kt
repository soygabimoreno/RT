package com.appacoustic.rt.framework.extension

import org.junit.Assert.assertTrue
import org.junit.Test

class DoubleArrayExtensionKtTest {

    @Test
    fun `check getSizeInBytes() returns the expected values`() {
        assertTrue(0 == doubleArrayOf().getSizeInBytes())
        assertTrue(8 == DoubleArray(1).getSizeInBytes())
    }

    @Test
    fun `check getSizeInKB() returns the expected values`() {
        assertTrue(0f == doubleArrayOf().getSizeInKB())
        assertTrue(0.5f == DoubleArray(64).getSizeInKB())
        assertTrue(1f == DoubleArray(128).getSizeInKB())
    }

    @Test
    fun `check getSizeInMB() returns the expected values`() {
        assertTrue(0f == doubleArrayOf().getSizeInMB())
        assertTrue(0.5f == DoubleArray(65536).getSizeInMB())
        assertTrue(1f == DoubleArray(131072).getSizeInMB())
    }
}
