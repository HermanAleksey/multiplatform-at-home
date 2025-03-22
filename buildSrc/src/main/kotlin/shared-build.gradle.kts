plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
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
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    )
        .takeIf { "XCODE_VERSION_MAJOR" in System.getenv().keys } // Export the framework only for Xcode builds
        ?.forEach {
            // This `shared` framework is exported for app-ios-swift
            it.binaries.framework {
                baseName = "shared"

                export(Version.Decompose.Decompose)
                export(Version.Decompose.EssentyLifecycle)
            }
        }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(Version.Decompose.Decompose)
                api(Version.Decompose.EssentyLifecycle)
                implementation(Version.Decompose.DecomposeExtension)

                // Compose Libraries
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)

                // coroutines
                implementation(Version.Coroutines.Core)
                implementation(Version.Ktor.Http)
                implementation(Version.Ktor.ClientCore)
                implementation(Version.Ktor.ClientContentNegotiation)
                implementation(Version.Ktor.SerializationKotlinX)

                implementation(project(Modules.Model.Common))

                androidMain.dependencies {
                    implementation(Version.Ktor.ClientAndroid)
                }
                iosMain.dependencies {
                    implementation(Version.Ktor.ClientDarwin)
                }
                jvmMain.dependencies {
                    implementation(Version.Ktor.ClientOkHttp)
                }
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