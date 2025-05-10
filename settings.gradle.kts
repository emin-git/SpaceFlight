import java.net.URI

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        maven {
            url = uri("https://maven.pkg.github.com/emin-git/SpaceFlight")
            credentials {
                username = System.getenv("GITHUB_USERNAME") ?: ""
                password = System.getenv("GITHUB_TOKEN") ?: ""
            }
        }
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

buildCache {
    local {
        isEnabled = true
    }
    remote(HttpBuildCache::class.java) {
        url = URI("http://www.omdbapi.com/")
        isPush = true
        isAllowInsecureProtocol = true
        // https server a baglanmak istediginde isAllowInsecureProtocol bunu kaldır ve url i yenile
    }
}

// build.gradle.kts (Kotlin DSL)
gradle.startParameter.excludedTaskNames.add(":build-logic:convention:testClasses")

rootProject.name = "SpaceFlight"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":core")
include(":core:common")
include(":core:database")
include(":core:model")
include(":core:network")
include(":core:utils")
include(":domain")
include(":domain:interactor")
include(":domain:repository")
include(":domain:usecase")
include(":testing")
include(":testing:fake")
include(":testing:helper")
include(":testing:mock")
include(":testing:rules")
include(":presentation")
include(":presentation:basefeature")
include(":presentation:newsfeature")
include(":core:ui")
include(":spaceFlightSdk")