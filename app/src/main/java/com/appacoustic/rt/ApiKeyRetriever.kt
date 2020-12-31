package com.appacoustic.rt

object ApiKeyRetriever {

    init {
        System.loadLibrary("api-keys")
    }

    external fun getAmplitudeApiKey(): String
}
