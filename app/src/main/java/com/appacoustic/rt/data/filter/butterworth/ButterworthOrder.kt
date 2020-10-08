package com.appacoustic.rt.data.filter.butterworth

import androidx.annotation.StringRes
import com.appacoustic.rt.R

enum class ButterworthOrder(
    val nOrder: Int,
    @StringRes val stringResId: Int
) {
    N_1(1, R.string.spinner_order_1),
    N_2(2, R.string.spinner_order_2),
    N_4(4, R.string.spinner_order_4),
    N_8(8, R.string.spinner_order_8)
}
