package com.appacoustic.rt.data.persistent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Persistent(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var testSignalEnabled: Boolean
)
