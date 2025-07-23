plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Model.Ftp))
                implementation(project(Modules.Shared.Core.Utils))
                implementation(project(Modules.Shared.Core.UiKit))
                implementation(project(Modules.Shared.Core.BaseDatabase))
                implementation(project(Modules.Shared.Core.Navigation))

                implementation(Dependencies.Room.Runtime)
                implementation(Dependencies.Koin.Core)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.features.settings"
}