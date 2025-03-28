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
                implementation(project(Modules.Shared.Core.UiKit))

                val coilVersion = "2.3.8"
                implementation("com.github.skydoves:landscapist-coil3:$coilVersion")

                // filePicker
                implementation(Dependencies.FilePicker.Core)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.ftp"
}