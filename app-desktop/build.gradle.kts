import org.jetbrains.compose.desktop.application.dsl.TargetFormat


plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        // deprecated, but doesn't compile without it
        @Suppress("DEPRECATION")
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(Modules.Compose.Theme))
                implementation(project(Modules.Shared.Root))

                implementation(compose.desktop.currentOs)
                implementation(Dependencies.Decompose.DecomposeExtension)
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
            packageVersion = "1.0.0"
        }
    }
}
