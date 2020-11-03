package com.appacoustic.rt.framework.rating

import org.koin.dsl.module

val inAppRateModule = module {
    single { InAppRate(errorTrackerComponent = get()) }
}
