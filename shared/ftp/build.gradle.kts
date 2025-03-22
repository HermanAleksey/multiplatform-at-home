plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Model.Ftp))
                implementation(project(Modules.Shared.Utils))
                implementation(project(Modules.Compose.Utils))
                implementation(project(Modules.Compose.Theme))

                // filePicker
                implementation(Version.FilePicker.Core)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.ftp"
}