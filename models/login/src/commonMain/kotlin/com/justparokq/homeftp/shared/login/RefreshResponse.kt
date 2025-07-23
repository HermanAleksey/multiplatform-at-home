package com.justparokq.homeftp.shared.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshResponse(
    @SerialName("token")
    val token: String,
    @SerialName("refreshToken")
    val refreshToken: String,
) 