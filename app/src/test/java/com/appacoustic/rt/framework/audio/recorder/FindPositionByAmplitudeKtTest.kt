package com.appacoustic.rt.framework.audio.recorder

import org.junit.Assert.assertTrue
import org.junit.Test

class FindPositionByAmplitudeKtTest {

    @Test
    fun `check expected values`() {
        val doubles = DoubleArray(8)
        doubles[0] = 1.0
        doubles[1] = 14.0
        doubles[2] = 0.0
        doubles[3] = 0.0
        doubles[4] = -12.0
        doubles[5] = 2.0
        doubles[6] = 111.0
        doubles[7] = 4.0

        val position = doubles.findPositionByAmplitude(-5)

        assertTrue(3 == position)
    }
}
