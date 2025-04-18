plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Shared.Utils))
                implementation(project(Modules.Shared.Core.Navigation))
                implementation(Dependencies.Koin.Core)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.main"
}