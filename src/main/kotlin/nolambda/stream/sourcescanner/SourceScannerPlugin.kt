package nolambda.stream.sourcescanner

import org.gradle.api.Plugin
import org.gradle.api.Project

class SourceScannerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register(CreateModuleTextTask.TASK_NAME, CreateModuleTextTask::class.java)
    }
}