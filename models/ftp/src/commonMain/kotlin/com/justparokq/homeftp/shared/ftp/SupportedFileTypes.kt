package com.justparokq.homeftp.shared.ftp

fun isTypeSupported(extension: String): Boolean {
    return isImage(extension) || isVideo(extension)
}

fun isImage(extension: String): Boolean {
    return ImageTypes.entries.any { it.extension.equals(extension, ignoreCase = true) }
}

fun isVideo(extension: String): Boolean {
    return VideoTypes.entries.any { it.extension.equals(extension, ignoreCase = true) }
}

enum class ImageTypes(val extension: String) {

    JPG("jpg"),
    PNG("png");
}

enum class VideoTypes(val extension: String) {

    MP4("mp4");
}