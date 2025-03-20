import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.compose")
//    id("com.android.application")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    applyDefaultHierarchyTemplate()

    jvm()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    )
        .takeIf { "XCODE_VERSION_MAJOR" in System.getenv().keys } // Export the framework only for Xcode builds
        ?.forEach {
            // This `shared` framework is exported for app-ios-swift
            it.binaries.framework {
                baseName = "shared" // Used in app-ios-swift

//                export(libs.decompose.decompose)
                export("com.arkivanov.decompose:decompose:3.1.0")
//                export(libs.essenty.lifecycle)
//                export("com.arkivanov.essenty:lifecycler:2.1.0")
            }
        }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("com.arkivanov.decompose:decompose:3.1.0")
//                api("com.arkivanov.essenty:lifecycler:2.1.0")

                implementation(project(Modules.Model.Common))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
                val ktorHttp = "1.1.5"
                implementation("io.ktor:ktor-http:$ktorHttp")
                implementation("io.ktor:ktor-client-core:$ktorHttp")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorHttp")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorHttp")

                // Compose Libraries
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.feature"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}