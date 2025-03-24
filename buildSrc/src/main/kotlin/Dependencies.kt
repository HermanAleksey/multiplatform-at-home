import org.gradle.api.JavaVersion

object Dependencies {

    object Android {

        val CompileSdk = 34
        val MinSdk = 24
        val TargetSdk = 34

        val VersionCode = 1
        val VersionName = "1.0.0"
    }

    object AndroidX {

        private const val activityComposeVersion = "1.9.0"

        val ActivityCompose = "androidx.activity:activity-compose:$activityComposeVersion"
    }

    object Java {

        val Version = JavaVersion.VERSION_11
    }

    object Kotlin {

        private const val version = "2.0.0"

        val JUnit = "org.jetbrains.kotlin:kotlin-test-junit:$version"
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
        private const val version = "2.3.11"
        private const val clientVersion = "2.3.11"

        val Http = "io.ktor:ktor-http:$httpVersion"
        val ClientCore = "io.ktor:ktor-client-core:$httpVersion"
        val ServerCore = "io.ktor:ktor-server-core-jvm:$httpVersion"
        val ServerNettyJwm = "io.ktor:ktor-server-netty-jvm:$httpVersion"

        val ClientContentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        val ServerContentNegotiation = "io.ktor:ktor-server-content-negotiation:$version"
        val SerializationKotlinX = "io.ktor:ktor-serialization-kotlinx-json:$version"

        val ClientAndroid = "io.ktor:ktor-client-android:$clientVersion"
        val ClientDarwin = "io.ktor:ktor-client-darwin:$clientVersion"
        val ClientOkHttp = "io.ktor:ktor-client-okhttp:$clientVersion"
        val Tests = "io.ktor:ktor-server-tests-jvm:$clientVersion"

        val ServerAuth = "io.ktor:ktor-server-auth:$version"
        val ServerAuthJwt = "io.ktor:ktor-server-auth-jwt:$version"
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

    object Logback {

        private const val version = "1.5.6"

        val LogbackClassic = "ch.qos.logback:logback-classic:$version"
    }
}