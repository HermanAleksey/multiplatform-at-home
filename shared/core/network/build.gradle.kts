plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Koin.Core)
                implementation(project(Modules.Shared.Core.DataStore))
                implementation(project(Modules.Shared.Core.BaseDatabase))
                implementation(project(Modules.Shared.Core.SettingKey))
                implementation(project(Modules.Model.Login))
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.core.network"
}