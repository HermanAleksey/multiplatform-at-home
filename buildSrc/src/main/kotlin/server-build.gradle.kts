import org.gradle.kotlin.dsl.application
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

plugins {
    id("org.jetbrains.kotlin.jvm")
//    kotlin("jvm") version "2.1.0"
    id("io.ktor.plugin")
//    alias(libs.plugins.kotlin.jwm)
//    alias(libs.plugins.ktor)
//    application
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

    val logback = "1.5.6"
    implementation("ch.qos.logback:logback-classic:$logback")

//    val ktor = "2.3.12"
//    implementation("io.ktor:ktor-server-core-jvm:$ktor")
//    implementation("io.ktor:ktor-server-netty-jvm:$ktor")
//
//    val ktorServerAuth = "2.0.2"
////    implementation("io.ktor:ktor-server-auth")
//    implementation("io.ktor:ktor-server-auth:$ktorServerAuth")
//    implementation("io.ktor:ktor-server-auth-jwt")
////    implementation("io.ktor:ktor-server-content-negotiation")
//    val ktorVersion = "2.3.7"
//    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
//    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
//
//    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor")
//    val kotlin = "2.0.0"
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin")
}