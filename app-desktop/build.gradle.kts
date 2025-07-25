import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.compose.reload.ComposeHotRun
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag


plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
    id("org.jetbrains.compose.hot-reload")
}

composeCompiler {
    featureFlags.add(ComposeFeatureFlag.OptimizeNonSkippingGroups)
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
                implementation(project(Modules.Shared.Core.UiKit))
                implementation(project(Modules.Shared.Features.Root))
                implementation(project(Modules.Shared.Core.Utils))
                implementation(project(Modules.Shared.Core.Navigation))

                implementation(Dependencies.Koin.Core)

                implementation(compose.desktop.currentOs)
                implementation(Dependencies.Decompose.DecomposeExtension)
            }
        }
    }
}


// ./gradlew :app-desktop:createDistributable
// By default, it creates .app (on macOS), .exe (on Windows), .deb (on Linux) -
// but only those formats that are actually available on the current platform.
compose.desktop {
    application {
        mainClass = "com.justparokq.homeftp.desktop.MainKt"

        nativeDistributions {
            targetFormats(
                TargetFormat.Dmg,
                TargetFormat.Pkg,
                TargetFormat.Msi,
                TargetFormat.Deb,
                TargetFormat.Exe
            )
            packageName = "KMPet"
            packageVersion = "1.0.0"
            description = "Simple desktop client"

            windows {
                iconFile.set(project.file("src/jvmMain/resources/app_icon.ico"))
            }
            macOS {
                iconFile.set(project.file("src/jvmMain/resources/app_icon.icns"))
            }
            linux {
                iconFile.set(project.file("src/jvmMain/resources/app_icon.png"))
            }
        }
    }
}

// launch desktop HotReload app
tasks.register<ComposeHotRun>("runHot") {
    mainClass.set("com.justparokq.homeftp.desktop.HotReloadMainKt")
}