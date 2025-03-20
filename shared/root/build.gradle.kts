@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.decompose.decompose)
                api(libs.essenty.lifecycle)

                // Decompose Libraries
                api(libs.decompose.decompose)
                implementation(libs.decompose.extensionsComposeJetbrains)

                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Shared.Feature))
                implementation(project(Modules.Shared.Login))
                implementation(project(Modules.Shared.Main))
                implementation(project(Modules.Shared.Ftp))

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
    namespace = "com.justparokq.homefpt.shared.root"
}