object Modules {

    object Model {

        const val Common = ":models:common"
        const val Ftp = ":models:ftp"
        const val Login = ":models:login"
    }

    object Shared {

        object Core {

            const val UiKit = ":shared:core:ui_kit"
            const val BaseDatabase = ":shared:core:base_database"
            const val Navigation = ":shared:core:navigation"
            const val Network = ":shared:core:network"
        }

        object Features {

            const val Settings = ":shared:features:settings"
        }

        // todo move to core\feature folders
        const val Root = ":shared:root"
        const val Ftp = ":shared:ftp"
        const val Login = ":shared:login"
        const val Main = ":shared:main"
        const val Utils = ":shared:utils"
    }
}