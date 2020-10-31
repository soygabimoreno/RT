package com.appacoustic.rt.data.persistent.datasource

import com.appacoustic.rt.data.persistent.db.PersistentDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersistentDbDatasource(
    persistentDb: PersistentDb
) : PersistentLocalDatasource {

    private val persistentDao = persistentDb.persistentDao()

    override suspend fun isTestSignalEnabled(): Boolean = withContext(Dispatchers.IO) {
        persistentDao.isTestSignalEnabled()
    }

    override suspend fun setTestSignalEnabled(testSignalEnabled: Boolean): Unit = withContext(Dispatchers.IO) {
        persistentDao.setTestSignalEnabled(testSignalEnabled)
    }
}
