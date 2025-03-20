@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("compose-multiplatform-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Compose.Utils))
                implementation(project(Modules.Compose.Theme))
            }
        }
    }
}

android {
    namespace = "com.justparokq.homeftp.compose.common-components"
}
