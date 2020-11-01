package com.appacoustic.rt.domain.calculator

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficients
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder2
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder4
import com.appacoustic.rt.data.filter.butterworth.ButterworthCoefficientsOrder8
import com.appacoustic.rt.domain.Measure
import com.appacoustic.rt.domain.calculator.analytics.ReverbTimeCalculatorEvents
import com.appacoustic.rt.domain.calculator.processing.*
import com.appacoustic.rt.framework.audio.recorder.Recorder
import com.appacoustic.rt.framework.extension.getSizeInMB

class ReverbTimeCalculator(
    private val analyticsTrackerComponent: AnalyticsTrackerComponent
) {

    class EmptyByteArraySignalException : Exception("The ByteArray signal is empty.")
    class EmptyDoubleArraySignalException : Exception("The DoubleArray signal is empty.")

    operator fun invoke(xBytes: ByteArray, sampleRate: Int): Either<Throwable, List<Measure>> {
        return if (xBytes.isEmpty()) {
            EmptyByteArraySignalException().left()
        } else {
            try {
                analyticsTrackerComponent.trackEvent(
                    ReverbTimeCalculatorEvents.DataByteArraySize(
                        xBytes.getSizeInMB()
                    )
                )
                val x = xBytes
                    .toDoubleSamples()
                    .toDivisibleBy32()
                    .normalize()

                if (x.isEmpty()) {
                    EmptyDoubleArraySignalException().left()
                } else {
                    val dBStart = ReverbTimeMethod.EDT.dBStart
                    val dBEnd = ReverbTimeMethod.EDT.dBEnd
                    val positionDbStart125 = x.calculatePosition(
                        125,
                        2,
                        ButterworthCoefficientsOrder2.FREQUENCY_125,
                        dBStart
                    )
                    val positionDbEnd125 = x.calculatePosition(
                        125,
                        2,
                        ButterworthCoefficientsOrder2.FREQUENCY_125,
                        dBEnd
                    )
                    val positionDbStart250 = x.calculatePosition(
                        250,
                        4,
                        ButterworthCoefficientsOrder4.FREQUENCY_250,
                        dBStart
                    )
                    val positionDbEnd250 = x.calculatePosition(
                        250,
                        4,
                        ButterworthCoefficientsOrder4.FREQUENCY_250,
                        dBEnd
                    )
                    val positionDbStart500 = x.calculatePosition(
                        500,
                        4,
                        ButterworthCoefficientsOrder4.FREQUENCY_500,
                        dBStart
                    )
                    val positionDbEnd500 = x.calculatePosition(
                        500,
                        4,
                        ButterworthCoefficientsOrder4.FREQUENCY_500,
                        dBEnd
                    )
                    val positionDbStart1000 = x.calculatePosition(
                        1000,
                        4,
                        ButterworthCoefficientsOrder4.FREQUENCY_1000,
                        dBStart
                    )
                    val positionDbEnd1000 = x.calculatePosition(
                        1000,
                        4,
                        ButterworthCoefficientsOrder4.FREQUENCY_1000,
                        dBEnd
                    )
                    val positionDbStart2000 = x.calculatePosition(
                        2000,
                        8,
                        ButterworthCoefficientsOrder8.FREQUENCY_2000,
                        dBStart
                    )
                    val positionDbEnd2000 = x.calculatePosition(
                        4000,
                        8,
                        ButterworthCoefficientsOrder8.FREQUENCY_2000,
                        dBEnd
                    )
                    val positionDbStart4000 = x.calculatePosition(
                        8000,
                        8,
                        ButterworthCoefficientsOrder8.FREQUENCY_4000,
                        dBStart
                    )
                    val positionDbEnd4000 = x.calculatePosition(
                        8000,
                        8,
                        ButterworthCoefficientsOrder8.FREQUENCY_4000,
                        dBEnd
                    )

                    val reverbTime125 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd125 - positionDbStart125).toDouble() / sampleRate
                    val reverbTime250 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd250 - positionDbStart250).toDouble() / sampleRate
                    val reverbTime500 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd500 - positionDbStart500).toDouble() / sampleRate
                    val reverbTime1000 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd1000 - positionDbStart1000).toDouble() / sampleRate
                    val reverbTime2000 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd2000 - positionDbStart2000).toDouble() / sampleRate
                    val reverbTime4000 = 60 / (-dBEnd + dBStart).toDouble() * (positionDbEnd4000 - positionDbStart4000).toDouble() / sampleRate

                    listOf(
                        Measure(
                            Measure.Frequency.FREQUENCY_125,
                            reverbTime125.toFloat()
                        ),
                        Measure(
                            Measure.Frequency.FREQUENCY_250,
                            reverbTime250.toFloat()
                        ),
                        Measure(
                            Measure.Frequency.FREQUENCY_500,
                            reverbTime500.toFloat()
                        ),
                        Measure(
                            Measure.Frequency.FREQUENCY_1000,
                            reverbTime1000.toFloat()
                        ),
                        Measure(
                            Measure.Frequency.FREQUENCY_2000,
                            reverbTime2000.toFloat()
                        ),
                        Measure(
                            Measure.Frequency.FREQUENCY_4000,
                            reverbTime4000.toFloat()
                        )
                    ).right()
                }
            } catch (e: Exception) {
                e.left()
            }
        }
    }

    private fun DoubleArray.calculatePosition(
        frequency: Int,
        nOrder: Int,
        butterworthCoefficients: ButterworthCoefficients, // TODO: Remove these coefficients. They are useless
        amplitudeInDb: Int
    ) = this
//        .filterIIR(butterworthCoefficients) // TODO: Remove this filter. It is useless
        .filterIIRJ(
            frequency,
            nOrder,
            Recorder.SAMPLE_RATE
        )
        .muteStart(0.05, Recorder.SAMPLE_RATE)
        .schroederIntegral()
        .normalizeAndLinearToLogarithmic()
        .findPositionByAmplitude(amplitudeInDb)
}
