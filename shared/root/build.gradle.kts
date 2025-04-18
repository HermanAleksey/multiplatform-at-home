plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Shared.Core.Navigation))
                implementation(project(Modules.Shared.Login))
                implementation(project(Modules.Shared.Main))
                implementation(project(Modules.Shared.Ftp))
                implementation(project(Modules.Shared.Features.Settings))
                implementation(project(Modules.Shared.Core.BaseDatabase))
                implementation(project(Modules.Shared.Utils))
                implementation(Dependencies.Koin.Core)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.root"
}