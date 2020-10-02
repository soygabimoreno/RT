package com.appacoustic.rt.framework.audio.recorder

import org.junit.Assert.assertTrue
import org.junit.Test

class ToDoubleSamplesKtTest {

    @Test
    fun `check typical values`() {
        val bufferSize = 8
        val bytes = ByteArray(bufferSize)
        bytes[0] = 1
        bytes[1] = 0

        bytes[2] = 0
        bytes[3] = 1

        bytes[4] = 0
        bytes[5] = 2

        bytes[6] = 1
        bytes[7] = 1
        val doubles = bytes.toDoubleSamples()
        assertTrue(1.0 == doubles[0])
        assertTrue(256.0 == doubles[1])
        assertTrue(512.0 == doubles[2])
        assertTrue(257.0 == doubles[3])
    }
}
