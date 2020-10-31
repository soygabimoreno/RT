package com.appacoustic.rt.data.persistent.db

import com.appacoustic.rt.data.persistent.datasource.PersistentDbDatasource
import com.appacoustic.rt.data.persistent.datasource.PersistentLocalDatasource
import com.appacoustic.rt.data.persistent.repository.DefaultPersistentRepository
import com.appacoustic.rt.data.persistent.repository.PersistentRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val persistentModule = module {
    single { PersistentDb.build(androidContext()) }
    single<PersistentLocalDatasource> { PersistentDbDatasource(persistentDb = get()) }
    single<PersistentRepository> { DefaultPersistentRepository(persistentLocalDatasource = get()) }
}
