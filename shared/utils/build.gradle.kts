@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))
                
                androidMain.dependencies {
                    implementation(libs.ktor.client.android)
                }
                iosMain.dependencies {
                    implementation(libs.ktor.client.darwin)
                }
                jvmMain.dependencies {
                    implementation(libs.ktor.client.okhttp)
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.utils"
}