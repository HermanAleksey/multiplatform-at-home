package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import tasks.LaunchDesktopTask
import tasks.LaunchIosTask

class LauncherTaskPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("launchIosApp", LaunchIosTask::class.java)
        project.tasks.register("launchDesktopApp", LaunchDesktopTask::class.java)
//        project.tasks.register("launchAndroidApp", LaunchAndroidTask::class.java)

        project.tasks.register("runAllTargets") {
            group = "target-launchers"
            description = "Launches all targets"
            dependsOn("launchIosApp", "launchDesktopApp")
        }
    }
}