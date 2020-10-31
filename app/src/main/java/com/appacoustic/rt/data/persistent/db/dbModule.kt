package com.appacoustic.rt.data.persistent.db

import android.content.Context
import com.appacoustic.rt.data.persistent.datasource.PersistentDbDatasource
import com.appacoustic.rt.data.persistent.datasource.PersistentLocalDatasource
import com.appacoustic.rt.data.persistent.repository.PersistentRepository
import com.appacoustic.rt.data.persistent.repository.SharedPreferencesPersistentRepository
import com.appacoustic.rt.domain.Infrastructure
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val persistentModule = module {
    single { PersistentDb.build(androidContext()) }
    single<PersistentLocalDatasource> { PersistentDbDatasource(persistentDb = get()) }
    single<PersistentRepository> {
        SharedPreferencesPersistentRepository(
            sharedPreferences = androidApplication()
                .getSharedPreferences(
                    Infrastructure.SHARED_PREFERENCES_APP,
                    Context.MODE_PRIVATE
                )
        )
    }
}
