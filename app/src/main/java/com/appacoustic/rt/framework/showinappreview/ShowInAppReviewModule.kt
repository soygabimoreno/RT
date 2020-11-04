package com.appacoustic.rt.framework.showinappreview

import org.koin.dsl.module

val showInAppReviewModule = module {
    single { ShowInAppReview(errorTrackerComponent = get()) }
}
