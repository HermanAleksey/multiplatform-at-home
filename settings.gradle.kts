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


include(":shared:core:utils")
include(":shared:core:navigation")
include(":shared:core:ui_kit")
include(":shared:core:base_database")
include(":shared:core:network")
include(":shared:core:setting_key")
include(":shared:core:data_store")

include(":shared:features:settings")
include(":shared:features:ftp")
include(":shared:features:main")
include(":shared:features:root")
include(":shared:features:login")

include(":app-android")
include(":app-desktop")

include(":server")
