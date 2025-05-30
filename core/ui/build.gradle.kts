@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.plugin.ui.library)
    id("maven-publish")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("github") {
                groupId = "com.emingit.spaceflight"
                artifactId = project.name
                version = project.property("VERSION_NAME") as String
                artifact("build/outputs/aar/${project.name}-release.aar")
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/emin-git/spaceflight")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
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
        release {
            isMinifyEnabled = false
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