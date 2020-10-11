package com.appacoustic.rt.domain.calculator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.appacoustic.rt.R
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.framework.KLog
import com.appacoustic.rt.framework.audio.recorder.Recorder
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReverbTimeCalculatorAndroidTest {

    private val analyticsTrackerComponent = mockk<AnalyticsTrackerComponent>(relaxed = true)

    /**
     * We have a reference measures made by some MATLAB scripts and executed in Octave.
     *
     * These are the input values:
     * - audioFile: "clap.wav" (inside assets)
     * - dBStart: -5 dB
     * - dBEnd: -15 dB
     *
     * The measures are the following ones:
     * - 125 Hz: 0.31878
     * - 250 Hz: 0.35184
     * - 500 Hz: 0.35932
     * - 1000 Hz: 0.39510
     * - 2000 Hz: 0.39429
     * - 4000 Hz: 0.33143
     */

    @Test
    fun if_a_wav_file_read_is_used_as_input_byte_array_then_the_measures_are_the_expected_ones() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val inputStream = context.resources.openRawResource(R.raw.clap)
        val xBytes = inputStream.readBytes()

        val reverbTimeCalculator = ReverbTimeCalculator(analyticsTrackerComponent)
        reverbTimeCalculator(xBytes, Recorder.SAMPLE_RATE)
            .fold({
                KLog.e("exception: ${it.message}")
                fail()
            }, { measures ->
                val rt125 = measures[0].time
                val rt250 = measures[1].time
                val rt500 = measures[2].time
                val rt1000 = measures[3].time
                val rt2000 = measures[4].time
                val rt4000 = measures[5].time
                assertTrue(0.31878f == rt125)
                assertTrue(0.35184f == rt250)
                assertTrue(0.35932f == rt500)
                assertTrue(0.39510f == rt1000)
                assertTrue(0.39429f == rt2000)
                assertTrue(0.33143f == rt4000)
            })
    }

    private fun buildReverbTimeCalculator() = ReverbTimeCalculator(analyticsTrackerComponent)
}
