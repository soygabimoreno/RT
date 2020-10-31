package com.appacoustic.rt.data.persistent.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appacoustic.rt.data.persistent.Persistent

@Database(
    entities = [Persistent::class],
    version = 1
)
abstract class PersistentDb : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            PersistentDb::class.java,
            "persistent-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun persistentDao(): PersistentDao
}
