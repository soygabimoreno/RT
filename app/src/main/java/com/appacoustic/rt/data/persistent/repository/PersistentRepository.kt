package com.appacoustic.rt.data.persistent.repository

interface PersistentRepository {
    fun isTestSignalEnabled(): Boolean
    fun setTestSignalEnabled(testSignalEnabled: Boolean)
}
