package com.appacoustic.rt.data.persistent.repository

import com.appacoustic.rt.data.persistent.datasource.PersistentLocalDatasource

class DefaultPersistentRepository(
    private val persistentLocalDatasource: PersistentLocalDatasource
) : PersistentRepository {

    override suspend fun isTestSignalEnabled(): Boolean = persistentLocalDatasource.isTestSignalEnabled()

    override suspend fun setTestSignalEnabled(testSignalEnabled: Boolean) =
        persistentLocalDatasource.setTestSignalEnabled(testSignalEnabled)
}
