@file:Suppress("UnstableApiUsage")

include(":models:common")


include(":models:login")


include(":models:ftp")


pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "home-ftp"
include(":models:ftp")
include(":models:login")
include(":models:common")

include(":shared")
include(":shared:ftp")
include(":shared:login")
include(":shared:main")
include(":shared:root")
include(":shared:utils")
include(":shared:feature")

include(":server")
//include(":compose-ui")
include(":compose-ui:core:theme")
include(":compose-ui:core:utils")

include(":app-android")
include(":app-desktop")
