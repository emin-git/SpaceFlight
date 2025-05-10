@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.io.FileInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Properties

val keystorePropertiesFile: File = rootProject.file("keystore.properties")

plugins {
    alias(libs.plugins.plugin.application)
    alias(libs.plugins.plugin.hilt)
    alias(libs.plugins.plugin.firebase)
}

android {
    val majorUpdateVersion = DefaultConfig.MajorVersion
    val minorUpdateVersion = DefaultConfig.MinorVersion
    val patchVersion = DefaultConfig.PatchVersion

    val mVersionCode = majorUpdateVersion.times(LocalConstants.MajorVersionTime)
        .plus(minorUpdateVersion.times(LocalConstants.MinorVersionTime))
        .plus(patchVersion)

    val mVersionName = "$majorUpdateVersion.$minorUpdateVersion.$patchVersion"
    val formatter = DateTimeFormatter.ofPattern(LocalConstants.OffPattern)
    val currentTime = LocalDateTime.now().format(formatter)

    defaultConfig {
        versionCode = mVersionCode
        versionName = mVersionName
        applicationId = DefaultConfig.Project
    }

    signingConfigs {
        create("release") {
            if (keystorePropertiesFile.exists()) {
                val keystoreProperties = Properties()
                keystoreProperties.load(FileInputStream(keystorePropertiesFile))
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
    }
    packagingOptions {
        resources.excludes.add("META-INF/gradle/incremental.annotation.processors")
        resources.excludes.add("META-INF/LICENSE.md")
        resources.excludes.add("META-INF/LICENSE-notice.md")
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            applicationVariants.all {
                outputs.all {
                    (this as BaseVariantOutputImpl).outputFileName =
                        rootProject.name.replace(" ", "_") + "_" +
                                (buildType.name + "_v") +
                                (versionName + "_") +
                                "${currentTime}.apk"
                    println(outputFileName)
                }
            }
            signingConfig = if (keystorePropertiesFile.exists()) {
                signingConfigs.getByName("release")
            } else {
                signingConfigs.getByName("debug")

            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    namespace = DefaultConfig.Project
}

dependencies {

    moduleDomain()
    moduleCore()
    modulePresentation()
    moduleTesting()
    project.let {
        navigation(it)
        compose(it)
        //androidX(it)
        retrofit(it)
        hilt(it)
        apps(it)
    }
    api(libs.material)
}