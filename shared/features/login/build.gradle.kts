plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Login))
                implementation(project(Modules.Model.Common))
                implementation(project(Modules.Shared.Core.UiKit))
                implementation(project(Modules.Shared.Core.Navigation))
                implementation(project(Modules.Shared.Core.Network))
                implementation(project(Modules.Shared.Core.Utils))
                implementation(project(Modules.Shared.Core.BaseDatabase))
                implementation(project(Modules.Shared.Core.SettingKey))
                implementation(project(Modules.Shared.Core.DataStore))
                implementation(Dependencies.Koin.Core)

                jvmTest.dependencies {
                    implementation(kotlin("test"))
                    implementation(Dependencies.Ktor.Mock)
                    implementation(Dependencies.Coroutines.Test)
                    implementation(Dependencies.Test.Mockk)
                }
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.features.login"
}
