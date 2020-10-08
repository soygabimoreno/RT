package com.appacoustic.rt.data.filter.butterworth

import androidx.annotation.StringRes
import com.appacoustic.rt.R

enum class ButterworthFrequency(
    @StringRes val stringResId: Int
) {
    FREQUENCY_125(R.string.spinner_frequency_125),
    FREQUENCY_250(R.string.spinner_frequency_250),
    FREQUENCY_500(R.string.spinner_frequency_500),
    FREQUENCY_1000(R.string.spinner_frequency_1000),
    FREQUENCY_2000(R.string.spinner_frequency_2000),
    FREQUENCY_4000(R.string.spinner_frequency_4000),
}
