package com.justparokq.ftp.mapper

import com.justparokq.ftp.utils.PathProcessor
import com.justparokq.homeftp.shared.ftp.FileResponse
import java.io.File

internal class FileResponseMapper(
    private val pathProcessor: PathProcessor,
) {

    fun map(files: List<File>): List<FileResponse> {
        return files.map { file ->
            FileResponse(
                uri = file.path.removePrefix(pathProcessor.getRootDirectoryPath()),
                fileName = file.name,
                isDirectory = file.isDirectory,
            )
        }
    }
}