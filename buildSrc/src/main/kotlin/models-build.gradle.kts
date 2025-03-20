import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
//    alias(libs.plugins.kotlin.multiplatform)
//    alias(libs.plugins.android.library)
//    alias(libs.plugins.kotlin.serialization)
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
                baseName = "models" // Used in app-ios-swift
            }
        }

    sourceSets {
        val commonMain by getting {
            dependencies {
                val ktorVersion = "2.3.7"
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

            }
        }
        val commonTest by getting {
            dependencies {

            }
        }
    }
}

android {
    namespace = "com.justparokq.homeftp.models.common"
    compileSdk = 34//libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = 24//libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}