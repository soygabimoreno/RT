package com.appacoustic.rt.data.persistent.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface PersistentDao {

    @Query("SELECT testSignalEnabled FROM Persistent")
    fun isTestSignalEnabled(): Boolean

    @Query("UPDATE Persistent SET testSignalEnabled = :testSignalEnabled")
    fun setTestSignalEnabled(testSignalEnabled: Boolean)
}
