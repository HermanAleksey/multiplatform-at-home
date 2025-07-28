package com.justparokq.homeftp.shared.ftp.model


sealed interface FileSystemObject {

    // path in file system to this file
    val fullPath: Path

    // file name with extension
    val name: String

    data class Directory(
        override val name: String,
        override val fullPath: Path,
        val content: List<FileSystemObject>,
    ) : FileSystemObject

    sealed interface File {

        data class Image(
            override val name: String,
            override val fullPath: Path,
            // link for file to receive from server
            val serverLink: String,
        ) : FileSystemObject, File, Previewable {

            fun getPreviewUrl(): String {
                return super.getPreviewUrl(serverLink)
            }
        }

        data class Video(
            override val name: String,
            override val fullPath: Path,
        ) : FileSystemObject, File

        data object Unknown : FileSystemObject, File {
            override val fullPath: Path
                get() = Path("")
            override val name: String
                get() = ""
        }
    }
}

private interface Previewable {

    fun getPreviewUrl(url: String): String {
        return "$url&preview=true"
    }
}