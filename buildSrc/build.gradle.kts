plugins {
    `kotlin-dsl`
    kotlin("jvm") version "2.1.20"
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(libs.gradle)
    implementation(libs.compose.gradle.plugin)

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.org.jetbrains.kotlin.plugin.serialization.gradle.plugin)
    implementation(libs.org.jetbrains.kotlin.plugin.compose.gradle.plugin)
    implementation(libs.org.jetbrains.kotlin.android.gradle.plugin)
    implementation(libs.io.ktor.plugin.gradle.plugin)

    implementation("org.jetbrains.compose.hot-reload:gradle-plugin:1.0.0-alpha03")
}

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}