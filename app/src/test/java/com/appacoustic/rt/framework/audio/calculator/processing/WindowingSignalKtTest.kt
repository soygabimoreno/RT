package com.appacoustic.rt.framework.audio.calculator.processing

import org.junit.Assert.assertTrue
import org.junit.Test

class WindowingSignalKtTest {

    @Test
    fun `check the transformed signal only has the useful values`() {
        val doubles = DoubleArray(8)
        doubles[0] = 0.0
        doubles[1] = 200.0

        doubles[2] = 400.0
        doubles[3] = 1.0

        doubles[4] = 0.0
        doubles[5] = 2.0

        doubles[6] = 200.0
        doubles[7] = 0.0

        val windowedSignal = doubles.windowingSignal(300, 100)

        assertTrue(windowedSignal.size == 5)
    }
}
