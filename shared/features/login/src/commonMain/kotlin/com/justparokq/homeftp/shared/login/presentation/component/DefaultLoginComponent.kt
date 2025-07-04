package com.justparokq.homeftp.shared.login.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justparokq.homeftp.shared.login.LoginRequest
import com.justparokq.homeftp.shared.login.api.Active
import com.justparokq.homeftp.shared.login.api.LoginComponent
import com.justparokq.homeftp.shared.login.api.LoginComponentIntent
import com.justparokq.homeftp.shared.login.api.LoginComponentState
import com.justparokq.homeftp.shared.login.api.OnLoginButtonClick
import com.justparokq.homeftp.shared.login.api.OnNetworkSettingsChanged
import com.justparokq.homeftp.shared.login.api.OnPasswordFieldUpdated
import com.justparokq.homeftp.shared.login.api.OnUsernameFieldUpdated
import com.justparokq.homeftp.shared.login.network.LoginRepository
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature
import com.justparokq.homeftp.shared.utils.MainMultiplatform
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import com.justpoarokq.shared.core.base_database.api.NetworkSettingsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class DefaultLoginComponent(
    private val componentContext: ComponentContext,
    private val loginRepository: LoginRepository,
    private val featureNavigator: FeatureNavigator,
    private val settingsInteractor: NetworkSettingsInteractor,
) : LoginComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private val _state = MutableValue(Active())
    override val state: Value<LoginComponentState> = _state

    init {
        coroutineScope.launch {
            val target = settingsInteractor.getTargetOption()
            _state.update {
                it.copy(
                    networkOptionState = Active.NetworkOptionState(
                        selectedOption = target,
                    )
                )
            }
        }
    }

    override fun processIntent(intent: LoginComponentIntent) {
        when (intent) {
            OnLoginButtonClick -> onLoginButtonClick()
            is OnPasswordFieldUpdated -> onPasswordFieldUpdated(intent.newValue)
            is OnUsernameFieldUpdated -> onUsernameFieldUpdated(intent.newValue)
            is OnNetworkSettingsChanged -> onNetworkSettingsChanged(intent.option)
        }
    }

    private fun onUsernameFieldUpdated(newValue: String) {
        _state.update { it.copy(usernameTextField = newValue, errorMessage = null) }
    }

    private fun onPasswordFieldUpdated(newValue: String) {
        _state.update { it.copy(passwordTextField = newValue, errorMessage = null) }
    }

    private fun onLoginButtonClick() {
        coroutineScope.launch {
            val request = LoginRequest(
                username = _state.value.usernameTextField,
                password = _state.value.passwordTextField,
            )
            loginRepository.sendLoginRequest(request)
                .collect { response ->
                    when (response) {
                        is Result.Error -> {
                            _state.update { it.copy(errorMessage = response.errorMessage) }
                        }

                        is Result.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = response.loading
                                )
                            }
                        }

                        is Result.Success -> {
                            withContext(Dispatchers.MainMultiplatform()) {
                                featureNavigator.replaceCurrentWith(ProjectFeature.MAIN)
                            }
                        }
                    }
                }
        }
    }

    private fun onNetworkSettingsChanged(option: Setting.NetworkKey.Target.Option) {
        coroutineScope.launch {
            settingsInteractor.setTargetOption(option)
        }
    }
}
