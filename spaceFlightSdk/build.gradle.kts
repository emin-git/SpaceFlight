@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.plugin.ui.library)
    alias(libs.plugins.plugin.hilt)
    id("maven-publish")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("github") {
                groupId = "com.emingit.spaceflight"
                artifactId = "spaceflight-sdk"
                version = project.property("VERSION_NAME") as String
                from(components["release"])
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
    namespace = "com.emin.spaceFlightSdk"
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    project.let {
        compose(it)
        hilt(it)
        apps(it)
        security(it)
    }

    api(project(":core:common"))
    api(project(":core:database"))
    api(project(":core:model"))
    api(project(":core:network"))
    api(project(":core:utils"))

    api(project(":domain:interactor"))
    api(project(":domain:repository"))
    api(project(":domain:usecase"))

    api(project(":presentation:basefeature"))
}