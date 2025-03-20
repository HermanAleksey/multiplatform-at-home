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

                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Model.Ftp))
                implementation(project(Modules.Shared.Utils))
                implementation(project(Modules.Compose.Utils))
                implementation(project(Modules.Compose.Theme))

                // Decompose Libraries
                api(libs.decompose.decompose)
                implementation(libs.decompose.extensionsComposeJetbrains)

                // filePicker
                implementation(libs.filekit.core)

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
    namespace = "com.justparokq.homefpt.shared.ftp"
}