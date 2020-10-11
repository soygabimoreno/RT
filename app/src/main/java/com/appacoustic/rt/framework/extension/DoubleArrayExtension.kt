package com.appacoustic.rt.framework.extension

fun DoubleArray.getSizeInBytes(): Int {
    return 8 * size
}

fun DoubleArray.getSizeInKB(): Float {
    return getSizeInBytes().toFloat() / 1024
}

fun DoubleArray.getSizeInMB(): Float {
    return getSizeInKB() / 1024
}
