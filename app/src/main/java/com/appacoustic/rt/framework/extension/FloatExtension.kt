package com.appacoustic.rt.framework.extension

import java.text.DecimalFormat

fun Float.formatTo2Decimals(): String {
    val df = DecimalFormat("#,##0.00")
    return df.format(this)
}
