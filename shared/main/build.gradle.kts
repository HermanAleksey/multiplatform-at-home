@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Shared.Utils))
                implementation(project(Modules.Shared.Feature))

                implementation(libs.decompose.extensionsComposeJetbrains)

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
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.main"
}