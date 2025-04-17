import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

object DefaultConfig {
    const val Project = "com.emin.spaceflight"
    const val MajorVersion = 1
    const val MinorVersion = 0
    const val PatchVersion = 0
}

object LocalConstants {
    const val MajorVersionTime = 10_000
    const val MinorVersionTime = 100
    const val OffPattern = "yyyy_MM_dd_hh_mm_a"
}

fun Project.libs() = extensions.getByType<VersionCatalogsExtension>().named("libs")

// Implementation

fun DependencyHandler.compose(project: Project) {
    listOf(
        "compose-ui",
        "compose-graphics",
        "compose-tooling",
        "compose-runtime",
        "compose-foundation",
        "compose-material3-android",
        "compose-viewmodel"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.androidX(project: Project) {
    listOf(
        "androidx-core-ktx",
        "androidx-appcompat",
        "androidx-junit",
        "androidx-espresso-core",
        "material",
        "mockk",
        "kotlinxCoroutinesTest"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.retrofit(project: Project) {
    listOf(
        "retrofit",
        "gson",
        "converter-gson"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.database(project: Project) {
    listOf(
        "androidx-room-common",
        "androidx-room-ktx",
        "androidx-room-runtime-android"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.hilt(project: Project) {
    listOf(
        "hilt",
        "hilt-compiler",
        "hilt-navigation-compose"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.jetbrains(project: Project) {
    listOf(
        "jetbrains-gradle",
        "jetbrains-kotlin"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.room(project: Project) {
    listOf(
        "room-ktx",
        "room-runtime"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.http(project: Project) {
    listOf(
        "okhttp-core",
        "okhttp-logging"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.apps(project: Project) {
    listOf(
        "auto_service",
        "javax_annotation"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.security(project: Project) {
    listOf(
        "security-crypto"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }

}

fun DependencyHandler.gson(project: Project) {
    listOf(
        "gson"
    ).forEach { add("implementation", project.libs().findLibrary(it).get()) }

}
// Api

fun DependencyHandler.activity(project: Project) {
    listOf(
        "androidx-activity-ktx",
        "compose-activity"
    ).forEach {
        add("api", project.libs().findLibrary(it).get())
    }
}

fun DependencyHandler.lifecycle(project: Project) {
    listOf(
        "lifecycle-vm",
        "lifecycle-livedata",
        "compose-viewmodel",
        "compose-lifecycle-runtime",
        "lifecycle-runtime-ktx"
    ).forEach { add("api", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.navigation(project: Project) {
    listOf(
        "compose-activity",
        "androidx-navigation-fragment",
        "hilt-navigation-compose",
        "compose-navigation"
    ).forEach { add("api", project.libs().findLibrary(it).get()) }
}

fun DependencyHandler.coil(project: Project) {
    listOf(
        "coil-kt",
        "coil-kt-compose"
    ).forEach { add("api", project.libs().findLibrary(it).get()) }
}

internal fun DependencyHandler.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}
