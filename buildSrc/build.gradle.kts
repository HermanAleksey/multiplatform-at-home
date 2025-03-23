plugins {
    `kotlin-dsl`
    kotlin("jvm") version Dependencies.Kotlin.Version
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(Dependencies.Gradle.BuildTools)

    implementation(Dependencies.Compose.GradlePlugin)

    implementation(Dependencies.Kotlin.GradlePlugin)
    implementation(Dependencies.Kotlin.AndroidGradlePlugin)
    implementation(Dependencies.Kotlin.ComposeGradlePlugin)
    implementation(Dependencies.Kotlin.SerializationGradlePlugin)
    implementation(Dependencies.Kotlin.Stdlib)
    implementation(Dependencies.Kotlin.Reflect)

    implementation(Dependencies.Ktor.GradlePlugin)

}

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}