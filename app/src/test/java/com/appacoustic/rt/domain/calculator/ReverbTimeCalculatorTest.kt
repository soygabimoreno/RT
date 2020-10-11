package com.appacoustic.rt.domain.calculator

import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.domain.calculator.analytics.ReverbTimeCalculatorEvents
import com.appacoustic.rt.framework.extension.getSizeInMB
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Test

class ReverbTimeCalculatorTest {

    private val analyticsTrackerComponent = mockk<AnalyticsTrackerComponent>(relaxed = true)

    @Test
    fun `if ReverbTimeCalculator is invoked, then the analytics tracker is triggered`() {
        val reverbTimeCalculator = buildReverbTimeCalculator()
        val xBytes = byteArrayOf(0, 1, 2, 3)

        reverbTimeCalculator(xBytes, 8)

        val slot = slot<ReverbTimeCalculatorEvents.DataByteArraySize>()
        verify(exactly = 1) { analyticsTrackerComponent.trackEvent(capture(slot)) }
        assertTrue(xBytes.getSizeInMB() == slot.captured.parameters["SIZE_IN_MB"])
    }

    private fun buildReverbTimeCalculator() = ReverbTimeCalculator(analyticsTrackerComponent)
}
