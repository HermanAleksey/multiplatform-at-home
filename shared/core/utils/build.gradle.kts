plugins {
    id("shared-build")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(Modules.Model.Common))

                androidMain.dependencies {
                    implementation(Dependencies.AndroidX.ActivityCompose)
                }

                jvmMain.dependencies {
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.7.3")
                }
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
    namespace = "com.justparokq.homefpt.shared.core.utils"
}