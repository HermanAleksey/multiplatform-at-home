plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                // No dependencies except stdlib
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.core.setting_key"
}