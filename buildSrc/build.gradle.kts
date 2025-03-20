plugins {
    `kotlin-dsl`
    kotlin("jvm") version "2.1.0"
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation("com.android.tools.build:gradle:8.1.4")

    implementation("org.jetbrains.compose:compose-gradle-plugin:1.6.11")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.0")

    implementation("org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:2.1.10")
    implementation("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:2.1.10")

    // server
    implementation("io.ktor.plugin:io.ktor.plugin.gradle.plugin:3.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.0")

//    implementation("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:2.1.20-RC3")


//    implementation("org.jetbrains.kotlin:kotlin-compose-compiler-plugin:1.5.11")
//    implementation("org.jetbrains.kotlin:compose-compiler-gradle-plugin:1.5.11")
//    implementation(libs.plugin.kotlin)
//    implementation(libs.plugin.compose)
//    implementation(libs.plugin.serialization)
//    implementation(Dependencies.SqlDelight.gradlePlugin)
}

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20")
}