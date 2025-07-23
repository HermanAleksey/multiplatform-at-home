package com.justparokq.homeftp.shared.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(
    @SerialName("refreshToken")
    val refreshToken: String,
)
