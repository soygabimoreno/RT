import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.services)
    alias(libs.plugins.ksp)
}


android {

    val properties = Properties().apply {
        load(FileInputStream(project.file("../local.properties")))
    }

    signingConfigs {
        create("release") {
            storeFile = file(properties.getProperty("keystore.storeFile"))
            storePassword = properties.getProperty("keystore.storePassword")
            keyAlias = properties.getProperty("keystore.keyAlias")
            keyPassword = properties.getProperty("keystore.keyPassword")
        }
    }

    namespace = "com.appacoustic.rt"
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    defaultConfig {
        applicationId = "com.appacoustic.rt"
        minSdk = 23
        targetSdk = 35
        versionCode = 37
        versionName = "3.10.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    bundle {
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
        language {
            enableSplit = false
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            ext["enableCrashlytics"] = false
        }
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    ksp {
        arg("arrow.generate.optionals", "true")
    }

    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.config)

    implementation(libs.koin.androidx.scope)
    implementation(libs.koin.androidx.viewmodel)

    implementation(libs.arrow.core)
    implementation(libs.arrow.optics)
    ksp(libs.arrow.optics.ksp.plugin)

    implementation(libs.androidx.browser)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime)

    implementation(libs.firebase.config)

    implementation(libs.google.play.review)
    implementation(libs.karumi.dexter)
    implementation(libs.mpandroidchart)
    implementation(libs.amplitude)
    implementation(libs.okhttp)
    implementation(libs.gson)
    implementation(libs.lottie)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.iirj)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.mockk)
}
