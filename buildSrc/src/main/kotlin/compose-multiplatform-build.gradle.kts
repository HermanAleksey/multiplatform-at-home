@file:Suppress("DSL_SCOPE_VIOLATION")

import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.compose")
//    id("com.android.application")
    id("org.jetbrains.compose")
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
        iosSimulatorArm64()
    )
        .takeIf { "XCODE_VERSION_MAJOR" in System.getenv().keys } // Export the framework only for Xcode builds
        ?.forEach {
            // This `shared` framework is exported for app-ios-compose
            it.binaries.framework {
                baseName = "shared" // Used in app-ios-compose

                export(project(":shared"))
            }
        }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Compose Libraries
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)

                // Decompose Libraries
//                api(libs.decompose.decompose)
//                implementation(libs.decompose.extensionsComposeJetbrains)

//                implementation(project(Modules.Compose.Utils))
//                implementation(project(Modules.Compose.Theme))
            }
        }
    }
}

android {
//    namespace = "com.justparokq.homefpt.compose.common-components"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
