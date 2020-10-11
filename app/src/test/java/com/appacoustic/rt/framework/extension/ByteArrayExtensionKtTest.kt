package com.appacoustic.rt.framework.extension

import org.junit.Assert.assertTrue
import org.junit.Test

class ByteArrayExtensionKtTest {

    @Test
    fun `check getSizeInBytes() returns the expected values`() {
        assertTrue(0 == byteArrayOf().getSizeInBytes())
        assertTrue(1 == ByteArray(1).getSizeInBytes())
    }

    @Test
    fun `check getSizeInKB() returns the expected values`() {
        assertTrue(0f == byteArrayOf().getSizeInKB())
        assertTrue(0.5f == ByteArray(512).getSizeInKB())
        assertTrue(1f == ByteArray(1024).getSizeInKB())
    }

    @Test
    fun `check getSizeInMB() returns the expected values`() {
        assertTrue(0f == byteArrayOf().getSizeInMB())
        assertTrue(0.5f == ByteArray(524288).getSizeInMB())
        assertTrue(1f == ByteArray(1048576).getSizeInMB())
    }
}
