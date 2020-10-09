package com.appacoustic.rt

import android.app.Application
import com.amplitude.api.AmplitudeClient
import com.appacoustic.rt.data.analytics.AnalyticsEvent
import com.appacoustic.rt.data.analytics.AnalyticsTrackerComponent
import com.appacoustic.rt.data.analytics.error.ErrorTrackerComponent
import com.appacoustic.rt.data.remoteconfig.RemoteConfig
import com.appacoustic.rt.framework.KLog
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        KLog.launch(BuildConfig.DEBUG)
        initKoin()
        initFirebase()
        initAmplitude()
    }

    private fun initKoin() {
        startKoin {
            if (BuildConfig.DEBUG) logger(AndroidLogger(Level.ERROR))
            androidContext(this@App)
            modules(serviceLocator)
        }
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initAmplitude() {
        val remoteConfig: RemoteConfig by inject()
        val errorTrackerComponent: ErrorTrackerComponent by inject()
        val amplitudeClient: AmplitudeClient by inject()
        val analyticsTrackerComponent: AnalyticsTrackerComponent by inject()
        GlobalScope.launch {
            remoteConfig.getAmplitudeApiKey()
                .fold({
                    errorTrackerComponent.trackError(it)
                }, { amplitudeApiKey ->
                    amplitudeClient.initialize(this@App, amplitudeApiKey)
                    analyticsTrackerComponent.trackEvent(
                        FooEvent(
                            "foo 1",
                            mapOf(
                                "key1" to "value1"
                            )
                        )
                    )
                })
        }
    }

    data class FooEvent(
        override val name: String,
        override val parameters: Map<String, Any> = emptyMap()
    ) : AnalyticsEvent
}
