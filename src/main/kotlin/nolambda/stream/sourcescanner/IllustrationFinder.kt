package nolambda.stream.sourcescanner

import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile

abstract class IllustrationFinder : WholeTextScannerTask() {

    @get:OutputFile
    val outputFile: RegularFileProperty = project.objects.fileProperty().convention(
        project.layout.buildDirectory.file("sourcescanner/xmlresult.txt")
    )

    @Internal
    override fun getTargetRegex(): List<Regex> {
        return listOf(
            Regex("[a-zAz]*?:illustration_name=\"(.*)\"")
        )
    }

    private val stringBuilder = StringBuilder()

    override fun onResult(result: Map<Regex, List<MatchResult>>) {
        result.forEach { entry ->
            entry.value.forEach { matchResult ->
                stringBuilder.appendLine(matchResult.groupValues[1])
            }
        }
        outputFile.get().asFile.writeText(stringBuilder.toString())
    }

    companion object {
        const val TASK_NAME = "scanXmlIllustration"
    }
}
