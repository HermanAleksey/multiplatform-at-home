package com.justparokq.homeftp.shared.ftp.data.mapper

import com.justparokq.homeftp.shared.ftp.FileResponse
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject

internal interface FileSystemObjectMapper {

    fun toFileSystemObject(response: FileResponse): FileSystemObject
}

internal class FileSystemObjectMapperImpl : FileSystemObjectMapper {

    override fun toFileSystemObject(response: FileResponse): FileSystemObject = with(response) {
        return if (isDirectory) {
            FileSystemObject.Directory(name, emptyList())
        } else {
            when (getFileExtension(name.lowercase())) {
                "jpg", "png", "jpeg", "gif" -> FileSystemObject.File.Image(name, uri)
                "mp4", "avi", "mov" -> FileSystemObject.File.Video(name, uri)
                else -> FileSystemObject.File.Unknown
            }
        }
    }

    private fun getFileExtension(filename: String): String? {
        return filename.substringAfterLast(delimiter = '.', missingDelimiterValue = "")
            .takeIf { it.isNotBlank() }
    }
}