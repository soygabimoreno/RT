package com.appacoustic.rt.data

import com.appacoustic.rt.data.persistent.repository.PersistentRepository
import com.appacoustic.rt.domain.UserSession

class DefaultUserSession(
    private val persistentRepository: PersistentRepository
) : UserSession {

    private var recordAudioPermissionGranted = false

    override fun isRecordAudioPermissionGranted(): Boolean = recordAudioPermissionGranted

    override fun setRecordAudioPermissionGranted(recordAudioPermissionGranted: Boolean) {
        this.recordAudioPermissionGranted = recordAudioPermissionGranted
    }

    override suspend fun isTestSignalEnabled(): Boolean = persistentRepository.isTestSignalEnabled()

    override suspend fun setTestSignalEnabled(testSignalEnabled: Boolean) {
        persistentRepository.setTestSignalEnabled(testSignalEnabled)
    }
}
