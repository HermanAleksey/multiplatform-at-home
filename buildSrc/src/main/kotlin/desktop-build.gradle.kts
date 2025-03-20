@file:Suppress("DSL_SCOPE_VIOLATION")

import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.jvm
import org.gradle.kotlin.dsl.project
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
//    alias(libs.plugins.kotlin.multiplatform)
//    alias(libs.plugins.kotlin.compose)
//    alias(libs.plugins.jetbrains.compose)
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
                implementation("com.arkivanov.decompose:extensions-compose:$decompose")
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
