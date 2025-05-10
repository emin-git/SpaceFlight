buildscript {
    dependencies {
        classpath(libs.oss.licenses.plugin)
    }
}

plugins {
    alias(libs.plugins.kotlin.android) apply (false)
    alias(libs.plugins.android.library) apply (false)
    alias(libs.plugins.android.application) apply (false)
    alias(libs.plugins.kotlin.serialization) apply (false)
    alias(libs.plugins.dagger.hilt.android) apply (false)
    alias(libs.plugins.firebase.crashlytics) apply (false)
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply (false)
    alias(libs.plugins.ksp) apply (false)
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.gms) apply (false)
}

fun Project.collectTasksRecursively(name: String, modules: List<String>): List<Task> {
    return subprojects
        .filterNot { it.name in modules }
        .flatMap { subproject ->
            listOfNotNull(subproject.tasks.findByName(name)) + subproject.collectTasksRecursively(name, modules)
        }
}

val publishAllModules = tasks.register("publishAllModules") {
    val buildTasks = collectTasksRecursively("assembleRelease", listOf("app"))
    val publishTasks = collectTasksRecursively(
        "publishGithubPublicationToGitHubPackagesRepository",
        listOf("app")
    )
    dependsOn(buildTasks)
    finalizedBy(publishTasks)
}

dependencies {
    project(":app")

    project(":core")
    project(":core:common")
    project(":core:database")
    project(":core:model")
    project(":core:network")
    project(":core:utils")

    project(":domain")
    project(":domain:interactor")
    project(":domain:repository")
    project(":domain:usecase")

    project(":presentation")
    project(":presentation:basefeature")
    project(":presentation:newsfeature")

    project(":spaceFlightSdk")

    project(":testing")
    project(":testing:fake")
    project(":testing:helper")
    project(":testing:mock")
    project(":testing:rules")

}