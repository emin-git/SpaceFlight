package plugins

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            val javaVersion = libs.findVersion("java").get().toString()
            val minSdkVersion = libs.findVersion("minSdk").get().toString().toInt()
            val targetSdkVersion = libs.findVersion("targetSdk").get().toString().toInt()
            val compileSdkVersion = libs.findVersion("compileSdk").get().toString().toInt()
            val kotlinOptIn = libs.findVersion("kotlinOptIn").get().toString()
            val composeOptIn = libs.findVersion("composeOptIn").get().toString()
            val metaInf = libs.findVersion("metaInf").get().toString()
            val enableCompose = libs.findVersion("enableCompose").get().toString()
            val metricCompose = libs.findVersion("metricCompose").get().toString()
            val enableComCompiler = libs.findVersion("enableComCompiler").get().toString()
            val composeReports = libs.findVersion("composeReports").get().toString()

            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("com.google.android.gms.oss-licenses-plugin")
                apply("kotlinx-serialization")
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = compileSdkVersion

                defaultConfig {
                    minSdk = minSdkVersion
                    targetSdk = targetSdkVersion
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.valueOf("VERSION_$javaVersion")
                    targetCompatibility = JavaVersion.valueOf("VERSION_$javaVersion")
                }

                buildFeatures {
                    compose = true
                    buildConfig = true
                }

                packaging {
                    resources {
                        excludes += metaInf
                    }
                }
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                compilerOptions {
                    jvmTarget.set(JvmTarget.fromTarget(javaVersion))
                    freeCompilerArgs.addAll(
                        kotlinOptIn,
                        composeOptIn,
                    )
                }
            }

            extensions.configure<ComposeCompilerGradlePluginExtension> {
                fun Provider<String>.onlyIfTrue() =
                    flatMap { provider { it.takeIf(String::toBoolean) } }

                fun Provider<*>.relativeToRootProject(dir: String) = flatMap {
                    rootProject.layout.buildDirectory.dir(projectDir.toRelativeString(rootDir))
                }.map { it.dir(dir) }

                project.providers.gradleProperty(enableCompose)
                    .onlyIfTrue()
                    .relativeToRootProject(metricCompose)
                    .let(metricsDestination::set)

                project.providers.gradleProperty(enableComCompiler)
                    .onlyIfTrue()
                    .relativeToRootProject(composeReports)
                    .let(reportsDestination::set)
            }
        }
    }
}