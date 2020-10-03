package com.appacoustic.rt.framework.extension

import java.math.RoundingMode
import java.text.DecimalFormat

fun Float.formatTo2Decimals(): String {
    val df = DecimalFormat("#,##0.00")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this)
}
