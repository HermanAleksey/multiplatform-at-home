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

// launch desktop HotReload app
tasks.register<ComposeHotRun>("runHot") {
    mainClass.set("com.justparokq.homeftp.desktop.HotReloadMainKt")
}