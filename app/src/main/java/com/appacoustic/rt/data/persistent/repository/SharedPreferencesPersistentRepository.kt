package com.appacoustic.rt.data.persistent.repository

import android.content.SharedPreferences

class SharedPreferencesPersistentRepository(
    private val sharedPreferences: SharedPreferences
) : PersistentRepository {

    companion object {
        private const val TEST_SIGNAL_ENABLED = "TEST_SIGNAL_ENABLED"
    }

    override suspend fun isTestSignalEnabled(): Boolean {
        return sharedPreferences.getBoolean(
            TEST_SIGNAL_ENABLED,
            false
        )
    }

    override suspend fun setTestSignalEnabled(testSignalEnabled: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(
                TEST_SIGNAL_ENABLED,
                testSignalEnabled
            )
            .apply()
    }
}
