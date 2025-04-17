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
    namespace = "com.emin.testing.helper"
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
    project.let {
        apps(it)
    }
}