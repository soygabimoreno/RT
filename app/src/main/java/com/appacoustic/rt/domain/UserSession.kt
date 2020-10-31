package com.appacoustic.rt.domain

interface UserSession {
    fun isRecordAudioPermissionGranted(): Boolean
    fun setRecordAudioPermissionGranted(recordAudioPermissionGranted: Boolean)
    suspend fun isTestSignalEnabled(): Boolean
    suspend fun setTestSignalEnabled(testSignalEnabled: Boolean)
}
