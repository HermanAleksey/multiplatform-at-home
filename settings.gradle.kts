@file:Suppress("UnstableApiUsage")

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

include(":shared:ftp")
include(":shared:login")
include(":shared:main")
include(":shared:root")
include(":shared:utils")
include(":shared:feature")

include(":compose-ui:theme")
include(":compose-ui:utils")
include(":compose-ui:common-components")

include(":app-android")
include(":app-desktop")

include(":server")
