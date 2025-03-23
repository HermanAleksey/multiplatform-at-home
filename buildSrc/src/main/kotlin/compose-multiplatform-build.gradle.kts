@file:Suppress("DSL_SCOPE_VIOLATION")


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
}

kotlin {
    applyDefaultHierarchyTemplate()

    jvm()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = Dependencies.Java.Version.toString()
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
            }
        }
    }
}

android {
    compileSdk = Dependencies.Android.CompileSdk

    defaultConfig {
        minSdk = Dependencies.Android.MinSdk
    }

    compileOptions {
        sourceCompatibility = Dependencies.Java.Version
        targetCompatibility = Dependencies.Java.Version
    }
}
