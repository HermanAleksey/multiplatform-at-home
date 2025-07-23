plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Shared.Core.Utils))
                implementation(project(Modules.Shared.Core.Navigation))
                implementation(project(Modules.Shared.Core.BaseDatabase))
                implementation(Dependencies.Koin.Core)
                implementation(Dependencies.Coil.Coil3)
                implementation(project(Modules.Shared.Core.SettingKey))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.features.main"
}