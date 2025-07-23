package com.justparokq.homeftp.shared.login.presentation.composables


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
internal fun LoginCard(
    usernameTextValue: String,
    updateUsername: (String) -> Unit,
    passwordTextValue: String,
    updatePassword: (String) -> Unit,
    isLoading: Boolean,
    onLoginButtonClicked: () -> Unit,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val usernameFocusRequester = FocusRequester()
    val passwordFocusRequester = FocusRequester()

    ElevatedCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(24.dp)
        ) {
            LoginInputField(
                value = usernameTextValue,
                label = "Username",
                onValueChanged = { str -> updateUsername(str) },
                isError = false,
                errorText = null,
                isEnabled = !isLoading,
                focusRequester = usernameFocusRequester,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                testTag = "username text field",
                onKeyboardActions = { passwordFocusRequester.requestFocus() },
            )
            Spacer(modifier = Modifier.height(16.dp))
            LoginInputField(
                value = passwordTextValue,
                label = "Password",
                onValueChanged = { str -> updatePassword(str) },
                isError = false,
                errorText = null,
                isEnabled = !isLoading,
                focusRequester = passwordFocusRequester,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                testTag = "password text field",
                onKeyboardActions = { focusManager.clearFocus() },
            )
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                        )
                        .padding(vertical = 10.dp, horizontal = 12.dp)
                ) {
                    androidx.compose.material3.Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                LoginMainButton(
                    text = "Sign in",
                    onClick = { onLoginButtonClicked() },
                    isLoading = isLoading,
                    isClickable = !isLoading && errorMessage == null,
                    modifier = Modifier.animateContentSize()
                )
            }
        }
    }
}