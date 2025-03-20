@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
//    alias(libs.plugins.kotlin.multiplatform)
//    alias(libs.plugins.android.library)
//    alias(libs.plugins.kotlin.serialization)
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.decompose.decompose)
                api(libs.essenty.lifecycle)

                implementation(project(Modules.Model.Common))
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.feature"
}