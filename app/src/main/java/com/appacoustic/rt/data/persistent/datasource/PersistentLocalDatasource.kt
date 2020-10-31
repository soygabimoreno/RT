package com.appacoustic.rt.data.persistent.datasource

interface PersistentLocalDatasource {
    suspend fun isTestSignalEnabled(): Boolean
    suspend fun setTestSignalEnabled(testSignalEnabled: Boolean)
}
