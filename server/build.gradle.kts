plugins {
    id("org.jetbrains.kotlin.jvm")
    id("io.ktor.plugin")
    application
}

group = "com.justparokq"
version = "1.0.0"
application {
    mainClass.set("com.justparokq.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Xms512m")
}

dependencies {
    implementation(project(Modules.Model.Ftp))
    implementation(project(Modules.Model.Login))
    implementation(project(Modules.Model.Common))

    implementation(Dependencies.Logback.LogbackClassic)

    implementation(Dependencies.Ktor.ServerCore)
    implementation(Dependencies.Ktor.ServerNettyJwm)
    implementation(Dependencies.Ktor.ServerAuth)
    implementation(Dependencies.Ktor.ServerAuthJwt)
    implementation(Dependencies.Ktor.ServerContentNegotiation)
    implementation(Dependencies.Ktor.SerializationKotlinX)

    testImplementation(Dependencies.Ktor.Tests)
    testImplementation(Dependencies.Kotlin.JUnit)
}