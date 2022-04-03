package nolambda.stream.sourcescanner

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction

abstract class WholeTextScannerTask : DefaultTask() {

    @get:InputFile
    abstract val moduleTextFile: RegularFileProperty

    @TaskAction
    fun run() {
        val targetFile = moduleTextFile.asFile.get().readText()
        if (targetFile.isBlank()) {
            error("Target file is blank")
        }

        val regexes = getTargetRegex()
        val result = mutableMapOf<Regex, List<MatchResult>>()

        regexes.forEach { regex ->
            val matchResult = regex.findAll(targetFile).toList()
            if (matchResult.isNotEmpty()) {
                result[regex] = matchResult
            }
        }

        logger.info("Sending result of: $result")
        onResult(result)
    }

    abstract fun getTargetRegex(): List<Regex>
    abstract fun onResult(result: Map<Regex, List<MatchResult>>)
}
