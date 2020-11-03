package com.appacoustic.rt.framework.rating

import android.app.Activity
import com.appacoustic.rt.data.analytics.error.ErrorTrackerComponent
import com.google.android.play.core.review.ReviewManagerFactory

class InAppRate(
    private val errorTrackerComponent: ErrorTrackerComponent
) {

    operator fun invoke(activity: Activity) {
        val reviewManager = ReviewManagerFactory.create(activity)
        val requestFlow = reviewManager.requestReviewFlow()
        requestFlow.addOnCompleteListener { request ->
            if (request.isSuccessful) {
                val reviewInfo = request.result
                reviewInfo?.let {
                    val flow = reviewManager.launchReviewFlow(
                        activity,
                        it
                    )
                    flow.addOnFailureListener { exception ->
                        errorTrackerComponent.trackError(exception)
                    }
                }
            } else {
                errorTrackerComponent.trackError(Exception("IN_APP_RATE_REQUEST_FAILURE"))
            }
        }
    }
}
