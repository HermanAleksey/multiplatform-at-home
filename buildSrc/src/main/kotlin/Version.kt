import org.gradle.api.JavaVersion

object Version {

    object Android {

        val CompileSdk = 34
        val MinSdk = 24
    }

    object Java {

        val Version = JavaVersion.VERSION_11
    }

    object Decompose {

        private const val essentyVersion = "2.1.0"
        private const val decomposeVersion = "3.1.0"

        val EssentyLifecycle = "com.arkivanov.essenty:lifecycle:$essentyVersion"
        val Decompose = "com.arkivanov.decompose:decompose:$decomposeVersion"
        val DecomposeExtension = "com.arkivanov.decompose:extensions-compose:$decomposeVersion"
    }

    object Coroutines {

        private const val coroutinesVersion = "1.8.1"

        val Core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
    }

    object Ktor {

        private const val ktorHttpVersion = "1.1.5"
        private const val ktorVersion = "2.3.7"
        private const val ktorClientVersion = "2.3.11"

        val Http = "io.ktor:ktor-http:$ktorHttpVersion"
        val ClientCore = "io.ktor:ktor-client-core:$ktorHttpVersion"

        val ClientContentNegotiation = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
        val SerializationKotlinX = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"

        val ClientAndroid = "io.ktor:ktor-client-android:$ktorClientVersion"
        val ClientDarwin = "io.ktor:ktor-client-darwin:$ktorClientVersion"
        val ClientOkHttp = "io.ktor:ktor-client-okhttp:$ktorClientVersion"
    }

    object FilePicker {

        // todo up version
        private const val filekitComposeVersion = "0.8.8"

        val Core = "io.github.vinceglb:filekit-core:$filekitComposeVersion"
        val Dialogs = "io.github.vinceglb:filekit-dialogs:$filekitComposeVersion"
        val DialogsCompose = "io.github.vinceglb:filekit-dialogs-compose:$filekitComposeVersion"
        val Coil = "io.github.vinceglb:filekit-coil:$filekitComposeVersion"
    }
}