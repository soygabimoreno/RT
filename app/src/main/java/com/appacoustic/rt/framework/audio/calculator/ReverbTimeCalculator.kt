package com.appacoustic.rt.framework.audio.calculator

import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder2
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder4
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder8
import com.appacoustic.rt.domain.Measure
import com.appacoustic.rt.framework.audio.calculator.processing.*
import kotlin.math.round

class ReverbTimeCalculator {

    operator fun invoke(xBytes: ByteArray, sampleRate: Int): List<Measure> {
        // ERASE: Not required anymore if we don't start to measure after click (and finish before pressing stop button)
//        xBytes = xBytes.muteStartAndEnd(0.25, sampleRate)

        var x = xBytes.toDoubleSamples()
        x = x.windowingSignal(300, 100)
        x = x.toDivisibleBy32()
        x = x.normalize()

        var y125 = x.filterIIR(ButterworthCoefficientsOrder2.FREQUENCY_125)
        var y250 = x.filterIIR(ButterworthCoefficientsOrder4.FREQUENCY_250)
        var y500 = x.filterIIR(ButterworthCoefficientsOrder4.FREQUENCY_500)
        var y1000 = x.filterIIR(ButterworthCoefficientsOrder4.FREQUENCY_1000)
        var y2000 = x.filterIIR(ButterworthCoefficientsOrder8.FREQUENCY_2000)
        var y4000 = x.filterIIR(ButterworthCoefficientsOrder8.FREQUENCY_4000)

        y125 = y125.schroederIntegral()
        y250 = y250.schroederIntegral()
        y500 = y500.schroederIntegral()
        y1000 = y1000.schroederIntegral()
        y2000 = y2000.schroederIntegral()
        y4000 = y4000.schroederIntegral()

        y125 = y125.normalizeAndLinearToLogarithmic()
        y250 = y250.normalizeAndLinearToLogarithmic()
        y500 = y500.normalizeAndLinearToLogarithmic()
        y1000 = y1000.normalizeAndLinearToLogarithmic()
        y2000 = y2000.normalizeAndLinearToLogarithmic()
        y4000 = y4000.normalizeAndLinearToLogarithmic()

        val dBStart = ReverbTimeMethod.EDT.dBStart
        val dBEnd = ReverbTimeMethod.EDT.dBEnd
        val positionDbStart125 = y125.findPositionByAmplitude(dBStart)
        val positionDbEnd125 = y125.findPositionByAmplitude(dBEnd)
        val positionDbStart250 = y250.findPositionByAmplitude(dBStart)
        val positionDbEnd250 = y250.findPositionByAmplitude(dBEnd)
        val positionDbStart500 = y500.findPositionByAmplitude(dBStart)
        val positionDbEnd500 = y500.findPositionByAmplitude(dBEnd)
        val positionDbStart1000 = y1000.findPositionByAmplitude(dBStart)
        val positionDbEnd1000 = y1000.findPositionByAmplitude(dBEnd)
        val positionDbStart2000 = y2000.findPositionByAmplitude(dBStart)
        val positionDbEnd2000 = y2000.findPositionByAmplitude(dBEnd)
        val positionDbStart4000 = y4000.findPositionByAmplitude(dBStart)
        val positionDbEnd4000 = y4000.findPositionByAmplitude(dBEnd)

        val reverbTime125 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd125 - positionDbStart125).toDouble() / sampleRate
        val reverbTime250 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd250 - positionDbStart250).toDouble() / sampleRate
        val reverbTime500 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd500 - positionDbStart500).toDouble() / sampleRate
        val reverbTime1000 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd1000 - positionDbStart1000).toDouble() / sampleRate
        val reverbTime2000 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd2000 - positionDbStart2000).toDouble() / sampleRate
        val reverbTime4000 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd4000 - positionDbStart4000).toDouble() / sampleRate

        return listOf(
            Measure(Measure.Frequency.FREQUENCY_125, (round(reverbTime125 * 10) / 10).toFloat()),
            Measure(Measure.Frequency.FREQUENCY_250, (round(reverbTime250 * 10) / 10).toFloat()),
            Measure(Measure.Frequency.FREQUENCY_500, (round(reverbTime500 * 10) / 10).toFloat()),
            Measure(Measure.Frequency.FREQUENCY_1000, (round(reverbTime1000 * 10) / 10).toFloat()),
            Measure(Measure.Frequency.FREQUENCY_2000, (round(reverbTime2000 * 10) / 10).toFloat()),
            Measure(Measure.Frequency.FREQUENCY_4000, (round(reverbTime4000 * 10) / 10).toFloat()),
        )
    }
}
