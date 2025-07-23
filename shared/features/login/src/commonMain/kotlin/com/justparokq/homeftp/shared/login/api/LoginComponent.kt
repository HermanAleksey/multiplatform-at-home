package com.justparokq.homeftp.shared.login.api

import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponentIntent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponentState

interface LoginComponent : BaseComponent<LoginComponentState, LoginComponentIntent>

sealed interface LoginComponentIntent : BaseComponentIntent

internal data object Initialize : LoginComponentIntent
internal data class OnUsernameFieldUpdated(val newValue: String) : LoginComponentIntent
internal data class OnPasswordFieldUpdated(val newValue: String) : LoginComponentIntent
internal data object OnLoginButtonClick : LoginComponentIntent
internal data class OnNetworkSettingsChanged(
    val option: Setting.NetworkKey.Target.Option
) : LoginComponentIntent

sealed interface LoginComponentState : BaseComponentState

internal data class Active(
    val usernameTextField: String = "",
    val passwordTextField: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val networkOptionState: NetworkOptionState? = null
) : LoginComponentState {

    val areCredentialsFilled: Boolean =
        usernameTextField.isNotBlank() && passwordTextField.isNotBlank()

    internal data class NetworkOptionState(
        val networkOptions: List<Setting.NetworkKey.Target.Option> = Setting.NetworkKey.Target.Option.entries,
        val selectedOption: Setting.NetworkKey.Target.Option
    )
}