package com.justparokq.homeftp.shared.ftp.data.mapper

import com.justparokq.homefpt.shared.core.network.url.UrlResolver
import com.justparokq.homeftp.shared.ftp.FileResponse
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.Path

internal interface FileSystemObjectMapper {

    suspend fun toFileSystemObject(response: FileResponse): FileSystemObject
}

internal class FileSystemObjectMapperImpl(
    private val urlResolver: UrlResolver
) : FileSystemObjectMapper {

    override suspend fun toFileSystemObject(response: FileResponse): FileSystemObject =
        with(response) {
            return if (isDirectory) {
                FileSystemObject.Directory(
                    name = fileName,
                    fullPath = Path(uri),
                    content = emptyList(),
                )
            } else {
                when (getFileExtension(fileName.lowercase())) {
                    "jpg", "png", "jpeg", "gif" -> FileSystemObject.File.Image(
                        fileName,
                        fullPath = Path(uri),
                        serverLink = "${urlResolver.getBaseUrl()}/image?path=${uri}"
                    )

                    "mp4", "avi", "mov" -> FileSystemObject.File.Video(
                        name = fileName,
                        fullPath = Path(uri)
                    )

                    else -> FileSystemObject.File.Unknown
                }
            }
        }

    private fun getFileExtension(filename: String): String? {
        return filename.substringAfterLast(delimiter = '.', missingDelimiterValue = "")
            .takeIf { it.isNotBlank() }
    }
}