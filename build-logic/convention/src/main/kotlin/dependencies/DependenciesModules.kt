import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

fun DependencyHandler.moduleCoreCommon() = implementation(project(Modules.Core.common))
fun DependencyHandler.moduleCoreDatabase() = implementation(project(Modules.Core.database))
fun DependencyHandler.moduleCoreModel() = implementation(project(Modules.Core.model))
fun DependencyHandler.moduleCoreUi() = implementation(project(Modules.Core.ui))
fun DependencyHandler.moduleCoreNetwork() = implementation(project(Modules.Core.network))
fun DependencyHandler.moduleCoreUtils() = implementation(project(Modules.Core.utils))
fun DependencyHandler.moduleDomainInteractor() = implementation(project(Modules.Domain.interactor))
fun DependencyHandler.moduleDomainRepository() = implementation(project(Modules.Domain.repository))
fun DependencyHandler.moduleDomainUseCase() = implementation(project(Modules.Domain.usecase))
fun DependencyHandler.moduleTestingFake() = implementation(project(Modules.Testing.fake))
fun DependencyHandler.moduleTestingHelper() = implementation(project(Modules.Testing.helper))
fun DependencyHandler.moduleTestingMock() = implementation(project(Modules.Testing.mock))
fun DependencyHandler.moduleTestingRules() = implementation(project(Modules.Testing.rules))
fun DependencyHandler.modulePresentationBaseFeature() = implementation(project(Modules.Presentation.baseFeature))
fun DependencyHandler.modulePresentationNewsFeature() = implementation(project(Modules.Presentation.news))


fun DependencyHandler.moduleCore() {
    moduleCoreModel()
    moduleCoreUtils()
    moduleCoreNetwork()
    moduleCoreCommon()
    moduleCoreDatabase()
    moduleCoreUi()
}

fun DependencyHandler.moduleDomain(){
    moduleDomainInteractor()
    moduleDomainRepository()
    moduleDomainUseCase()
}

fun DependencyHandler.modulePresentation(){
    modulePresentationBaseFeature()
    modulePresentationNewsFeature()
}

fun DependencyHandler.moduleTesting(){
    moduleTestingFake()
    moduleTestingMock()
    moduleTestingHelper()
    moduleTestingRules()
}