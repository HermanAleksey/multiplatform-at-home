plugins {
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.android.application")
    id("org.jetbrains.compose")
}

android {
    namespace = "com.justparokq.homeftp.android"
    compileSdk = Dependencies.Android.CompileSdk

    defaultConfig {
        applicationId = "com.justparokq.homeftp.android"
        minSdk = Dependencies.Android.MinSdk
        targetSdk = Dependencies.Android.TargetSdk
        versionCode = Dependencies.Android.VersionCode
        versionName = Dependencies.Android.VersionName
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = Dependencies.Java.Version
        targetCompatibility = Dependencies.Java.Version
    }

    kotlinOptions {
        jvmTarget = Dependencies.Java.Version.toString()
    }
}

dependencies {
    implementation(project(Modules.Shared.Features.Root))
    implementation(project(Modules.Shared.Core.UiKit))
    implementation(project(Modules.Shared.Core.Utils))

    implementation(Dependencies.AndroidX.ActivityCompose)
    implementation(compose.foundation)

    // file picker
    implementation(Dependencies.FilePicker.Core)
    implementation(Dependencies.FilePicker.Compose)

    // di
    implementation(Dependencies.Koin.Core)
}
