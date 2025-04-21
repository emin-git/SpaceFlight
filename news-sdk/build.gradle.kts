plugins {
    alias(libs.plugins.plugin.library)
    alias(libs.plugins.plugin.ui.library)
    id("maven-publish")
}

android {
    namespace = "com.emin.sdk.newssdk"
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
        minSdk = defaultConfig.minSdk
    }
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

group = "com.github.emin-git"
version = "1.0.2"

publishing {
    publications {
        create<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }

            groupId = "com.github.emin-git"
            artifactId = "news-sdk"
            version = "1.0.2"
        }
    }
}