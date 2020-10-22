package com.appacoustic.rt.framework.extension

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream

class ByteArrayOutputStreamExtensionKtTest {

    private val baos = ByteArrayOutputStream()

    @Before
    fun setUp() {
        baos.write(byteArrayOf())
    }

    @Test
    fun `check getSizeInBytes() returns the expected values`() {
        assertTrue(0 == baos.getSizeInBytes())

        baos.write(ByteArray(1))
        assertTrue(1 == baos.getSizeInBytes())
    }

    @Test
    fun `check getSizeInKB() returns the expected values`() {
        assertTrue(0f == baos.getSizeInKB())

        baos.write(ByteArray(512))
        assertTrue(0.5f == baos.getSizeInKB())

        baos.write(ByteArray(512))
        assertTrue(1f == baos.getSizeInKB())
    }

    @Test
    fun `check getSizeInMB() returns the expected values`() {
        assertTrue(0f == baos.getSizeInMB())

        baos.write(ByteArray(524288))
        assertTrue(0.5f == baos.getSizeInMB())

        baos.write(ByteArray(524288))
        assertTrue(1f == baos.getSizeInMB())
    }
}
