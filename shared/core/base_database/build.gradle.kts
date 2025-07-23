plugins {
    id("shared-build")
    id("androidx.room") version Dependencies.Room.version
    id("com.google.devtools.ksp") version Dependencies.Ksp.version
    // for desktop database
    kotlin("kapt")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Room.Runtime)
                implementation(Dependencies.Sqlite.Bundled)

                // di
                implementation(Dependencies.Koin.Core)

                implementation(project(Modules.Shared.Core.Utils))
                implementation(project(Modules.Shared.Core.SettingKey))
            }
        }
    }
}

dependencies {
    add("kspAndroid", Dependencies.Room.Compiler)
    add("kspIosSimulatorArm64", Dependencies.Room.Compiler)
    add("kspIosX64", Dependencies.Room.Compiler)
    add("kspIosArm64", Dependencies.Room.Compiler)
    add("kspJvm", Dependencies.Room.Compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "com.justparokq.homeftp.shared.core.base_database"
}
