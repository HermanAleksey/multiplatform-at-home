@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    //trick: for the same plugin versions in all sub-modules

//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.android.library) apply false
//    alias(libs.plugins.kotlin.android) apply false
//    alias(libs.plugins.kotlin.compose) apply false
//    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
//    alias(libs.plugins.jetbrains.compose) apply false
//    alias(libs.plugins.kotlin.jwm) apply false
}

//tasks.register("clean", Delete::class) {
//    delete(rootProject.layout.buildDirectory)
//}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}