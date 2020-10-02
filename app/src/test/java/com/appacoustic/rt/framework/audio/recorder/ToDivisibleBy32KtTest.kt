package com.appacoustic.rt.framework.audio.recorder

import org.junit.Assert.assertTrue
import org.junit.Test

class ToDivisibleBy32KtTest {

    @Test
    fun `check array with size larger than a number divisible by 32 indexes removes the last positions`() {
        val doubles = DoubleArray(35)
        doubles[30] = 1.0
        doubles[31] = 2.0
        doubles[32] = 3.0

        val res = doubles.toDivisibleBy32()

        val newSize = res.size
        assertTrue(32 == newSize)
        assertTrue(1.0 == res[newSize - 2])
        assertTrue(2.0 == res[newSize - 1])
    }
}
