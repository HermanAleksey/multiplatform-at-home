package com.justparokq.homeftp.shared.ftp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileResponse(
    @SerialName("uri")
    val uri: String,
    @SerialName("name")
    val fileName: String,
    @SerialName("isFolder")
    val isDirectory: Boolean,
)
