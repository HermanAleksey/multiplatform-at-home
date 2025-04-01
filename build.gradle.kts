import java.io.ByteArrayOutputStream

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven("https://plugins.gradle.org/m2/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

tasks.register("runAllTargets") {
    group = "application"
    description = "Запускает Android, iOS и Desktop приложения параллельно"

    doLast {
        // not working 4now
//        val androidProcess = exec {
//            commandLine("./gradlew", "launchAndroidApp")
//            isIgnoreExitValue = true
//        }

        val iosProcess = exec {
            commandLine("./gradlew", "launchIosApp")
            isIgnoreExitValue = true
        }

        val desktopProcess = exec {
            // or launchDesktopApp
            commandLine("./gradlew", ":app-desktop:run")
            isIgnoreExitValue = true
        }
    }
}

tasks.register("launchAndroidApp") {
    group = "application"
    description = "Installs and launches Android app on connected device/emulator"

    doLast {
        val devices = ByteArrayOutputStream()
        exec {// todo abd troubles
            commandLine("adb", "devices")
            standardOutput = devices
        }

        // Proceed only if devices are available
        if (devices.toString().contains("device")) {
            exec {
                workingDir = rootDir
                commandLine("./gradlew", ":app-android:installDebug")
            }
        } else {
            logger.lifecycle("No connected devices, starting emulator...")
            exec {
                commandLine("emulator", "-avd", "Pixel 6 API 33")
            }

            // Wait for device to be ready
            Thread.sleep(10_000)

            exec {
                workingDir = rootDir
                commandLine("./gradlew", ":app-android:installDebug")
            }
        }
        exec {
            commandLine(
                "adb", "shell", "am", "start",
                "-n", "com.justparokq.homeftp.MainActivity"
            )
        }
    }
}

tasks.register("launchDesktopApp") {
    group = "application"
    description = "Запускает Desktop-версию приложения"

    doLast {
        exec {
            workingDir = rootDir
            commandLine("./gradlew", ":app-desktop:run")
        }
    }
}

tasks.register("launchIosApp") {
    group = "application"
    description = "Запускает iOS-версию приложения в симуляторе"

    doLast {
        val projectDir = "${rootDir.path}/app-ios-compose"
        val buildDir = "$projectDir/build"
        val appPath = "$buildDir/Build/Products/Debug-iphonesimulator/app-ios-compose.app"
        val bundleId = "orgIdentifier.app-ios-compose"

        exec {
            commandLine("open", "-F", "-a", "Simulator", "--args", "-StartLastDevice")
        }

        exec {
            commandLine(
                "xcodebuild",
                "-scheme", "app-ios-compose",
                "-destination", "platform=iOS Simulator,name=iPhone 15,OS=17.5",
                "-project", "$projectDir/app-ios-compose.xcodeproj",
                "-derivedDataPath", buildDir,
                "build"
            )
        }

        exec {
            commandLine(
                "xcrun", "simctl", "install", "booted", appPath
            )
        }

        // Launch the application (with a delay for stability). Give the simulator time to start
        Thread.sleep(3000)
        exec {
            commandLine(
                "xcrun", "simctl", "launch", "booted", bundleId
            )
        }
    }
}