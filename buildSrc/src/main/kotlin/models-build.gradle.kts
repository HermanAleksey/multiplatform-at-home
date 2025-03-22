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
                jvmTarget = JavaVersion.VERSION_11.toString()
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
                val ktorVersion = "2.3.7"
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

            }
        }
    }
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}