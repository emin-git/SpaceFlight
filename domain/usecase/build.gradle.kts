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
    namespace = "com.emin.domain.usecase"
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
    moduleCore()
    moduleDomainRepository()
    project.let {
        apps(it)
    }
}