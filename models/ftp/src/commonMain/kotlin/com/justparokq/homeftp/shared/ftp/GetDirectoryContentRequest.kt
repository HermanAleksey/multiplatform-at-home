package com.justparokq.homeftp.shared.ftp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDirectoryContentRequest(
    @SerialName("uri")
    val uri: String,
)