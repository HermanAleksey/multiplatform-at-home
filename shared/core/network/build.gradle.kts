plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Koin.Core)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.core.network"
}