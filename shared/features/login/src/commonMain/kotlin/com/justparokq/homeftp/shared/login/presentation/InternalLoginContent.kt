package com.justparokq.homeftp.shared.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.justparokq.homeftp.shared.login.api.Active
import com.justparokq.homeftp.shared.login.api.LoginComponent
import com.justparokq.homeftp.shared.login.api.OnLoginButtonClick
import com.justparokq.homeftp.shared.login.api.OnNetworkSettingsChanged
import com.justparokq.homeftp.shared.login.api.OnPasswordFieldUpdated
import com.justparokq.homeftp.shared.login.api.OnUsernameFieldUpdated
import com.justparokq.homeftp.shared.login.presentation.component.PreviewLoginComponent
import com.justparokq.homeftp.shared.login.presentation.composables.LoginCard
import com.justparokq.homeftp.shared.login.presentation.composables.OptionsPopupLabel
import com.justparokq.homeftp.theme.AppTheme
import com.justparokq.homeftp.tooling.Preview

@Composable
internal fun InternalLoginContent(component: LoginComponent) {
    val state by component.state.subscribeAsState()
    val activeState = if (state is Active) state as Active else return

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        LoginCard(
            usernameTextValue = activeState.usernameTextField,
            updateUsername = { component.processIntent(OnUsernameFieldUpdated(it)) },
            passwordTextValue = activeState.passwordTextField,
            updatePassword = { component.processIntent(OnPasswordFieldUpdated(it)) },
            isLoading = activeState.isLoading,
            onLoginButtonClicked = { component.processIntent(OnLoginButtonClick) },
            errorMessage = activeState.errorMessage,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .shadow(20.dp)
                .defaultMinSize(minWidth = 300.dp, minHeight = 260.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        activeState.networkOptionState?.let {
            OptionsPopupLabel(
                networkOptionState = activeState.networkOptionState,
                onOptionSelected = { component.processIntent(OnNetworkSettingsChanged(it)) },
                modifier = Modifier,
            )
        }
    }
}

@Preview
@Composable
fun LoginContentPreviewLight() {
    AppTheme(darkTheme = true) {
        InternalLoginContent(PreviewLoginComponent())
    }
}

@Preview
@Composable
fun LoginContentPreviewDark() {
    AppTheme(darkTheme = false) {
        InternalLoginContent(PreviewLoginComponent())
    }
}
