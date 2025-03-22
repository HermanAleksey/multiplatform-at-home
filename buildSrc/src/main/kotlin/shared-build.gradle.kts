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

                implementation(project(Modules.Model.Common))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
                val ktorHttp = "1.1.5"
                implementation("io.ktor:ktor-http:$ktorHttp")
                implementation("io.ktor:ktor-client-core:$ktorHttp")
                val ktorVersion = "2.3.7"
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                // Compose Libraries
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)

                val ktorClientDarwin = "2.3.11"
                androidMain.dependencies {
                    implementation("io.ktor:ktor-client-android:$ktorClientDarwin")
                }
                iosMain.dependencies {
                    implementation("io.ktor:ktor-client-darwin:$ktorClientDarwin")
                }
                jvmMain.dependencies {
                    implementation("io.ktor:ktor-client-okhttp:$ktorClientDarwin")
                }
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