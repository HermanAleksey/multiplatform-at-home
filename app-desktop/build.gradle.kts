@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
//    alias(libs.plugins.kotlin.multiplatform)
//    alias(libs.plugins.kotlin.compose)
//    alias(libs.plugins.jetbrains.compose)
    id("desktop-build")
}


kotlin {
    jvm {
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(Modules.Compose.Theme))
                implementation(project(Modules.Shared.Root))

                implementation(compose.desktop.currentOs)
                val decompose = "3.1.0"
                implementation(libs.decompose.extensionsComposeJetbrains)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.justparokq.homeftp.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KotlinMultiplatformComposeDesktopApplication"
            packageVersion = "1.0.0"//libs.versions.project.get()
        }
    }
}
