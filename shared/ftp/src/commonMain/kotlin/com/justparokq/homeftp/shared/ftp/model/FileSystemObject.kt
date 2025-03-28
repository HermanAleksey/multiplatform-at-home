package com.justparokq.homeftp.shared.ftp.model

import com.justparokq.homeftp.shared.utils.localhostUrl

sealed interface FileSystemObject {

    data class Directory(
        val name: String,
        val content: List<FileSystemObject>,
    ) : FileSystemObject

    sealed interface File {

        data class Image(
            val name: String,
            val remotePath: String,
        ) : FileSystemObject, File, Previewable {

            fun getFullUrl(): String {
                return "http://$localhostUrl:8080/image?path=${remotePath}"
            }
        }

        data class Video(
            val name: String,
            val url: String,
        ) : FileSystemObject, File

        data object Unknown : FileSystemObject, File
    }
}

interface Previewable {

    fun getPreviewUrl(url: String): String {
        return "$url&preview=true"
    }
}