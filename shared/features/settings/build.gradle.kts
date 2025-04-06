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
                implementation(project(Modules.Shared.Core.BaseDatabase))

                implementation(Dependencies.Room.Runtime)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.features.settings"
}