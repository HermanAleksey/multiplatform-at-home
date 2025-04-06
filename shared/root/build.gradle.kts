plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Shared.Feature))
                implementation(project(Modules.Shared.Login))
                implementation(project(Modules.Shared.Main))
                implementation(project(Modules.Shared.Ftp))
                implementation(project(Modules.Shared.Features.Settings))
                implementation(project(Modules.Shared.Utils))
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.root"
}