package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import javax.inject.Inject

abstract class LaunchDesktopTask @Inject constructor(
    private val execOps: ExecOperations
) : DefaultTask() {
    @TaskAction
    fun launchDesktop() {
        execOps.exec {
            workingDir = project.rootDir
            commandLine("./gradlew", ":app-desktop:run")
        }
    }
}