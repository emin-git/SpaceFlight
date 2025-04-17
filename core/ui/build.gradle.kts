@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.plugin.ui.library)
}

android {
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
    namespace = "com.emin.core.ui"
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
    moduleCoreCommon()
    api(libs.androidx.appcompat)
    api(libs.androidx.fragment.ktx)
    project.let {
        activity(it)
        lifecycle(it)
        navigation(it)
        coil(it)
        apps(it)
    }
    api(libs.lottie)
}