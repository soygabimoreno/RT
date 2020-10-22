package com.appacoustic.rt.framework.extension

import java.io.ByteArrayOutputStream

fun ByteArrayOutputStream.getSizeInBytes(): Int {
    return size()
}

fun ByteArrayOutputStream.getSizeInKB(): Float {
    return getSizeInBytes().toFloat() / 1024
}

fun ByteArrayOutputStream.getSizeInMB(): Float {
    return getSizeInKB() / 1024
}
