@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.plugin.library)
    alias(libs.plugins.plugin.hilt)
}

android {
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
    namespace = "com.emin.core.common"
    buildTypes {
        debug {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.kotlinx.coroutines.android)
    api(libs.kotlinx.serialization.json)
    api(libs.kotlinx.datetime)
    api(libs.hilt)
    project.let {
        apps(it)
    }
}