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
    }
}

rootProject.name = "home-ftp"
include(":models")
include(":shared")
include(":server")
//include(":compose-ui")
include(":compose-ui:core:theme")
include(":compose-ui:core:utils")
include(":compose-ui:feature:authentication")
include(":compose-ui:feature:root")
include(":compose-ui:feature:main")
include(":compose-ui:feature:ftp")

include(":app-android")
include(":app-desktop")
