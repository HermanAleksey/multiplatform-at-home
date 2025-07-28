plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))

                implementation(project(Modules.Shared.Core.Navigation))
                implementation(project(Modules.Shared.Core.BaseDatabase))
                implementation(project(Modules.Shared.Core.Network))
                implementation(project(Modules.Shared.Core.Utils))
                implementation(project(Modules.Shared.Core.DataStore))

                implementation(project(Modules.Shared.Features.Login))
                implementation(project(Modules.Shared.Features.Main))
                implementation(project(Modules.Shared.Features.Ftp))
                implementation(project(Modules.Shared.Features.Settings))

                implementation(Dependencies.Koin.Core)
                implementation(Dependencies.Coil.Compose)
                implementation(Dependencies.Coil.NetworkKtor)
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.features.root"
}