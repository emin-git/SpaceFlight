@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.plugin.ui.library)
    alias(libs.plugins.plugin.hilt)
    kotlin("android")
}

android {
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
        minSdk = defaultConfig.minSdk
    }
    namespace = "com.emin.presentation.basefeature"
    packagingOptions {
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE-notice.md")
    }
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
    implementation(libs.androidx.foundation.layout.android)
    moduleCoreModel()
    moduleCoreCommon()
    project.let {
        compose(it)
        hilt(it)
        coil(it)
        apps(it)
    }
}