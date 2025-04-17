@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.plugin.ui.library)
    alias(libs.plugins.plugin.hilt)
}

android {
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
        minSdk = defaultConfig.minSdk
    }
    namespace = "com.emin.presentation.newsfeature"
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
    project.let {
        compose(it)
        hilt(it)
        apps(it)
        security(it)
        androidX(it)
    }
    moduleCore()
    moduleDomain()
    modulePresentationBaseFeature()
}