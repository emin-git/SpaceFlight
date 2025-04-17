import org.gradle.kotlin.dsl.compileOnly
import org.gradle.kotlin.dsl.gradlePlugin
import org.gradle.kotlin.dsl.libs
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.emin.build.logic"

val javaVersion = libs.versions.java.get().toInt()

java {
    sourceCompatibility = JavaVersion.values()[javaVersion - 1]
    targetCompatibility = JavaVersion.values()[javaVersion - 1]
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.valueOf("JVM_$javaVersion"))
    }
}

dependencies {
    compileOnly(libs.gradlePlugin.kotlin)
    compileOnly(libs.gradlePlugin.android)
    compileOnly(libs.gradlePlugin.compose)
}

gradlePlugin {
    plugins {
        register("application") {
            id = "com.emin.application"
            implementationClass = "plugins.ApplicationPlugin"
        }
        register("firebase") {
            id = "com.emin.firebase"
            implementationClass = "plugins.FirebasePlugin"
        }
        register("hilt") {
            id = "com.emin.hilt"
            implementationClass = "plugins.HiltPlugin"
        }
        register("library") {
            id = "com.emin.library"
            implementationClass = "plugins.LibraryPlugin"
        }
        register("uiLibrary") {
            id = "com.emin.ui.library"
            implementationClass = "plugins.UiLibraryPlugin"
        }
    }
}