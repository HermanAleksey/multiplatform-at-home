package com.justparokq.homeftp.shared.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
)