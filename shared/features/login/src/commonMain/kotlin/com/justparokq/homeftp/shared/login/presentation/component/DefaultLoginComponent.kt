package com.justparokq.homeftp.shared.login.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.login.LoginRequest
import com.justparokq.homeftp.shared.login.api.Active
import com.justparokq.homeftp.shared.login.api.LoginComponent
import com.justparokq.homeftp.shared.login.api.LoginComponentIntent
import com.justparokq.homeftp.shared.login.api.LoginComponentState
import com.justparokq.homeftp.shared.login.api.OnLoginButtonClick
import com.justparokq.homeftp.shared.login.api.OnPasswordFieldUpdated
import com.justparokq.homeftp.shared.login.api.OnUsernameFieldUpdated
import com.justparokq.homeftp.shared.login.network.LoginNetworkComponent
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature
import com.justparokq.homeftp.shared.utils.MainMultiplatform
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class DefaultLoginComponent(
    private val componentContext: ComponentContext,
    private val loginNetworkComponent: LoginNetworkComponent,
    private val featureNavigator: FeatureNavigator,
) : LoginComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private val _state = MutableValue(Active())
    override val state: Value<LoginComponentState> = _state

    override fun processIntent(intent: LoginComponentIntent) {
        when (intent) {
            OnLoginButtonClick -> onLoginButtonClick()
            is OnPasswordFieldUpdated -> onPasswordFieldUpdated(intent.newValue)
            is OnUsernameFieldUpdated -> onUsernameFieldUpdated(intent.newValue)
        }
    }

    private fun onUsernameFieldUpdated(newValue: String) {
        _state.update { it.copy(usernameTextField = newValue) }
    }

    private fun onPasswordFieldUpdated(newValue: String) {
        _state.update { it.copy(passwordTextField = newValue) }
    }

    private fun onLoginButtonClick() {
        coroutineScope.launch {
            val request = LoginRequest(
                username = _state.value.usernameTextField,
                password = _state.value.passwordTextField,
            )
            loginNetworkComponent.sendLoginRequest(request)
                .collect { response ->
                    when (response) {
                        is Result.Error -> {
                            // show error
                            println("onLoginButtonClick: error $response")
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
}
