package com.appacoustic.rt.data

import com.appacoustic.rt.domain.UserSession

class DefaultUserSession : UserSession {

    private var recordAudioPermissionGranted = false

    override fun isRecordAudioPermissionGranted(): Boolean = recordAudioPermissionGranted

    override fun setRecordAudioPermissionGranted(recordAudioPermissionGranted: Boolean) {
        this.recordAudioPermissionGranted = recordAudioPermissionGranted
    }
}
