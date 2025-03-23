import org.gradle.api.JavaVersion

object Dependencies {

    object Android {

        val CompileSdk = 34
        val MinSdk = 24
        val TargetSdk = 34

        val VersionCode = 1
        val VersionName = "1.0.0"
    }

    object Java {

        val Version = JavaVersion.VERSION_11
    }

    object Kotlin {

        val Version = "2.1.0"
        private const val gradlePluginVersion = "2.1.10"

        val Reflect = "org.jetbrains.kotlin:kotlin-reflect:$Version"
        val Stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$Version"

        val GradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$gradlePluginVersion"
        val SerializationGradlePlugin =
            "org.jetbrains.kotlin.plugin.serialization:org.jetbrains.kotlin.plugin.serialization.gradle.plugin:$gradlePluginVersion"
        val ComposeGradlePlugin =
            "org.jetbrains.kotlin.plugin.compose:org.jetbrains.kotlin.plugin.compose.gradle.plugin:$gradlePluginVersion"
        val AndroidGradlePlugin =
            "org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:$gradlePluginVersion"
    }

    object Gradle {

        private const val gradleVersion = "8.7.3"

        val BuildTools = "com.android.tools.build:gradle:$gradleVersion"
    }

    object Compose {

        private const val gradlePluginVersion = "1.7.3"

        val GradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:$gradlePluginVersion"
    }

    object Decompose {

        private const val essentyVersion = "2.1.0"
        private const val decomposeVersion = "3.1.0"

        val EssentyLifecycle = "com.arkivanov.essenty:lifecycle:$essentyVersion"
        val Decompose = "com.arkivanov.decompose:decompose:$decomposeVersion"
        val DecomposeExtension = "com.arkivanov.decompose:extensions-compose:$decomposeVersion"
    }

    object Coroutines {

        private const val version = "1.8.1"

        val Core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    }

    object Ktor {

        private const val httpVersion = "1.1.5"
        private const val version = "2.3.7"
        private const val clientVersion = "2.3.11"
        private const val gradlePluginVersion = "3.1.1"

        val Http = "io.ktor:ktor-http:$httpVersion"
        val ClientCore = "io.ktor:ktor-client-core:$httpVersion"

        val ClientContentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        val SerializationKotlinX = "io.ktor:ktor-serialization-kotlinx-json:$version"

        val ClientAndroid = "io.ktor:ktor-client-android:$clientVersion"
        val ClientDarwin = "io.ktor:ktor-client-darwin:$clientVersion"
        val ClientOkHttp = "io.ktor:ktor-client-okhttp:$clientVersion"

        val GradlePlugin = "io.ktor.plugin:io.ktor.plugin.gradle.plugin:$gradlePluginVersion"
    }

    object FilePicker {

        // todo up version
        private const val filekitComposeVersion = "0.8.8"

        val Core = "io.github.vinceglb:filekit-core:$filekitComposeVersion"
        val Compose = "io.github.vinceglb:filekit-compose:$filekitComposeVersion"
        val Dialogs = "io.github.vinceglb:filekit-dialogs:$filekitComposeVersion"
        val DialogsCompose = "io.github.vinceglb:filekit-dialogs-compose:$filekitComposeVersion"
        val Coil = "io.github.vinceglb:filekit-coil:$filekitComposeVersion"
    }
}