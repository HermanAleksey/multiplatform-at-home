plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Model.Ftp))
                implementation(project(Modules.Shared.Core.Network))
                implementation(project(Modules.Shared.Core.Utils))
                implementation(project(Modules.Shared.Core.UiKit))
                implementation(project(Modules.Shared.Core.Navigation))

                implementation(Dependencies.Coil.Coil3)
                implementation(Dependencies.Koin.Core)
                // filePicker
                implementation(Dependencies.FilePicker.Core)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.features.ftp"
}