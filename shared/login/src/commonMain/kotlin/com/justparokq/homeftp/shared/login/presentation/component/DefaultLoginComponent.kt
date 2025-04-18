package com.justparokq.homeftp.shared.login.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature
import com.justparokq.homeftp.shared.login.LoginRequest
import com.justparokq.homeftp.shared.login.model.LoginScreenModel
import com.justparokq.homeftp.shared.login.network.LoginNetworkComponent
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import kotlinx.coroutines.launch

class DefaultLoginComponent(
    private val componentContext: ComponentContext,
    private val loginNetworkComponent: LoginNetworkComponent,
    private val featureNavigator: FeatureNavigator,
) : LoginComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private val _state = MutableValue(LoginScreenModel())
    override val state: Value<LoginScreenModel> = _state

    override fun onUsernameFieldUpdated(newValue: String) {
        _state.update { it.copy(usernameTextField = newValue) }
    }

    override fun onPasswordFieldUpdated(newValue: String) {
        _state.update { it.copy(passwordTextField = newValue) }
    }

    override fun onLoginButtonClick() {
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
                            // navigate to next screen
                            featureNavigator.navigate(ProjectFeature.MAIN)
                        }
                    }
                }
        }
    }
}
