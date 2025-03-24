package com.justparokq.homeftp.shared.login.model

data class LoginScreenModel(
    val usernameTextField: String = "",
    val passwordTextField: String = "",
    val isLoading: Boolean = false,
)