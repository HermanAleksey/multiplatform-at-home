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

                implementation(project(Modules.Model.Login))
                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Compose.Utils))
                implementation(project(Modules.Compose.Theme))
                implementation(project(Modules.Shared.Utils))

                // Decompose Libraries
                api(libs.decompose.decompose)
                implementation(libs.decompose.extensionsComposeJetbrains)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.login"
}