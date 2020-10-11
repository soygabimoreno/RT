package com.appacoustic.rt.framework.extension

fun ByteArray.getSizeInBytes(): Int {
    return size
}

fun ByteArray.getSizeInKB(): Float {
    return getSizeInBytes().toFloat() / 1024
}

fun ByteArray.getSizeInMB(): Float {
    return getSizeInKB() / 1024
}
