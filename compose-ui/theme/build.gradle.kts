plugins {
    id("compose-multiplatform-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                // utils
                implementation(project(Modules.Compose.Utils))
            }
        }
    }
}

android {
    namespace = "com.justparokq.homeftp.compose.theme"
}
