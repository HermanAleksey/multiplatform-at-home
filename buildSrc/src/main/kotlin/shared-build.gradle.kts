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
                jvmTarget = Dependencies.Java.Version.toString()
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

                export(Dependencies.Decompose.Decompose)
                export(Dependencies.Decompose.EssentyLifecycle)
            }
        }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(Dependencies.Decompose.Decompose)
                api(Dependencies.Decompose.EssentyLifecycle)
                implementation(Dependencies.Decompose.DecomposeExtension)

                // Compose Libraries
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)

                // coroutines
                implementation(Dependencies.Coroutines.Core)

                // ktor
                implementation(Dependencies.Ktor.Http)
                implementation(Dependencies.Ktor.ClienCio)
                implementation(Dependencies.Ktor.ClientCore)
                implementation(Dependencies.Ktor.ClientContentNegotiation)
                implementation(Dependencies.Ktor.SerializationKotlinX)

                implementation(project(Modules.Model.Common))

                androidMain.dependencies {
                    implementation(Dependencies.Ktor.ClientAndroid)
                    implementation(Dependencies.Coroutines.Android)
                }
                iosMain.dependencies {
                    implementation(Dependencies.Ktor.ClientDarwin)
                }
                jvmMain.dependencies {
                    implementation(Dependencies.Ktor.ClientOkHttp)
                }
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