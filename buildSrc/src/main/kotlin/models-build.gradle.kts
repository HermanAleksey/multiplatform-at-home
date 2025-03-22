plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    applyDefaultHierarchyTemplate()

    jvm()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = Version.Java.Version.toString()
            }
        }
    }

    listOf(
        iosX64(), // Для симуляторов x86_64
        iosArm64(), // Для устройств ARM64 (iPhone, iPad)
        iosSimulatorArm64(), // Для симуляторов ARM64 (M1/M2)
    )
        .takeIf { "XCODE_VERSION_MAJOR" in System.getenv().keys } // Export the framework only for Xcode builds
        ?.forEach {
            // This `shared` framework is exported for app-ios-swift
            it.binaries.framework {
                baseName = "shared"
            }
        }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Version.Ktor.SerializationKotlinX)
            }
        }
    }
}

android {
    compileSdk = Version.Android.CompileSdk

    defaultConfig {
        minSdk = Version.Android.MinSdk
    }

    compileOptions {
        sourceCompatibility = Version.Java.Version
        targetCompatibility = Version.Java.Version
    }
}