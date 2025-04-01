package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

abstract class LaunchIosTask @Inject constructor(
    private val execOps: ExecOperations
) : DefaultTask() {
    @TaskAction
    fun launchIos() {
        val projectDir = "${project.rootDir.path}/app-ios-compose"
        val buildDir = "$projectDir/build"
        val appPath = "$buildDir/Build/Products/Debug-iphonesimulator/app-ios-compose.app"
        val bundleId = "orgIdentifier.app-ios-compose"

        execOps.exec {
            commandLine("open", "-F", "-a", "Simulator", "--args", "-StartLastDevice")
        }

        execOps.exec {
            commandLine(
                "xcodebuild",
                "-scheme", "app-ios-compose",
                "-destination", "platform=iOS Simulator,name=iPhone 15,OS=17.5",
                "-project", "$projectDir/app-ios-compose.xcodeproj",
                "-derivedDataPath", buildDir,
                "build"
            )
        }

        execOps.exec {
            commandLine("xcrun", "simctl", "install", "booted", appPath)
        }

        Thread.sleep(3000)
        execOps.exec {
            commandLine("xcrun", "simctl", "launch", "booted", bundleId)
        }
    }
}