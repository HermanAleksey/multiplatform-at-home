@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
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
include(":shared:core:ui_kit")
include(":shared:core:base_database")
include(":shared:features:settings")

include(":app-android")
include(":app-desktop")

include(":server")
