package com.appacoustic.rt.domain

import com.appacoustic.rt.framework.extension.formatTo2Decimals

fun List<Measure>.toHumanReadable(): String {
    val sb = StringBuilder()
    sb.append("[")
    forEachIndexed { index, measure ->
        sb.append(measure.time.formatTo2Decimals())
        if (index < size - 1) {
            sb.append(", ")
        }
    }
    sb.append("]")

    return sb.toString()
}
