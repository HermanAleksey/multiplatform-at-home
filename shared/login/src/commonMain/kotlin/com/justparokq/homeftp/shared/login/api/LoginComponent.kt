package com.justparokq.homeftp.shared.login.api

import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponentIntent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponentState

interface LoginComponent : BaseComponent<LoginComponentState, LoginComponentIntent>

sealed interface LoginComponentIntent : BaseComponentIntent

internal data class OnUsernameFieldUpdated(val newValue: String) : LoginComponentIntent
internal data class OnPasswordFieldUpdated(val newValue: String) : LoginComponentIntent
internal data object OnLoginButtonClick : LoginComponentIntent


sealed interface LoginComponentState : BaseComponentState

internal data class Active(
    val usernameTextField: String = "",
    val passwordTextField: String = "",
    val isLoading: Boolean = false,
) : LoginComponentState