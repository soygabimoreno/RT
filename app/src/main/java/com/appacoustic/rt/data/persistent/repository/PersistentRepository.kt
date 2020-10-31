package com.appacoustic.rt.data.persistent.repository

interface PersistentRepository {
    suspend fun isTestSignalEnabled(): Boolean
    suspend fun setTestSignalEnabled(testSignalEnabled: Boolean)
}
