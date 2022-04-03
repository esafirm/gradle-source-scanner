package nolambda.stream.sourcescanner

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class CreateModuleTextTask : DefaultTask() {

    @get:InputFiles
    val sourceFiles: FileTree = project.fileTree("src") {
        it.include("**/*.kt", "**/*.java", "**/*.xml")
    }

    @get:OutputFile
    val outputFile: RegularFileProperty = project.objects.fileProperty().convention(defaultOutputFile())

    @TaskAction
    fun run() {
        val result = StringBuilder()
        sourceFiles.visit { visitDetails ->
            if (visitDetails.isDirectory.not()) {
                result.appendLine(visitDetails.file.readText())
            }
        }
        val output = outputFile.get()
        output.asFile.writeText(result.toString())
    }

    private fun defaultOutputFile(): RegularFile {
        return project.layout.buildDirectory.file("sourcescanner/targetfile.txt").get()
    }

    companion object {
        const val TASK_NAME = "createModuleText"
    }
}