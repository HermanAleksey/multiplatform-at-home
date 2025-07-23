plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.core.navigation"
}