object Modules {

    const val app = ":app"

    object Core {
        const val common = ":core:common"
        const val database = ":core:database"
        const val model = ":core:model"
        const val network = ":core:network"
        const val utils = ":core:utils"
        const val ui = ":core:ui"
    }

    object Domain{
        const val interactor = ":domain:interactor"
        const val repository = ":domain:repository"
        const val usecase = ":domain:usecase"
    }

    object Presentation{
        const val baseFeature = ":presentation:basefeature"
        const val news = ":presentation:newsfeature"
    }

    object Testing{
        const val fake = ":testing:fake"
        const val helper = ":testing:helper"
        const val mock = ":testing:mock"
        const val rules = ":testing:rules"
    }
}