package com.justparokq.homeftp.shared.login.api

import androidx.compose.runtime.Composable
import com.justparokq.homeftp.shared.login.presentation.InternalLoginContent
import com.justparokq.homeftp.shared.login.presentation.component.PreviewLoginComponent
import com.justparokq.homeftp.theme.AppTheme
import com.justparokq.homeftp.tooling.Preview

@Composable
fun LoginContent(component: LoginComponent) {
    InternalLoginContent(component)
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
