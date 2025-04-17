package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val hiltAndroid = libs.findLibrary("hilt").get()
            val hiltCompiler = libs.findLibrary("hilt.compiler").get()

            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                "implementation"(hiltAndroid)
                "ksp"(hiltCompiler)
            }
        }
    }
}