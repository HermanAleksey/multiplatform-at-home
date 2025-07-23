plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                commonMain.dependencies {
                    implementation(Dependencies.Koin.Core)


                    implementation(Dependencies.DataStore.DataStore)
                    implementation(Dependencies.DataStore.Core)
                    implementation(Dependencies.DataStore.Preferences)
                }
            }
        }
    }
}

android {
    namespace = "com.justparokq.homefpt.shared.core.data_store"
}