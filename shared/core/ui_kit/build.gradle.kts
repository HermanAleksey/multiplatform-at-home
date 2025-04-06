plugins {
    id("compose-multiplatform-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
    }
}

android {
    namespace = "com.justparokq.homeftp.shared.core.ui_kit"
}
