package com.appacoustic.rt.domain

interface UserSession {
    fun isRecordAudioPermissionGranted(): Boolean
    fun setRecordAudioPermissionGranted(recordAudioPermissionGranted: Boolean)
}
