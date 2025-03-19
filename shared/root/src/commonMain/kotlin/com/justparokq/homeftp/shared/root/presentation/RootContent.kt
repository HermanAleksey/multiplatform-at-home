package com.justparokq.homeftp.shared.root.presentation

import MainContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.justparokq.homeftp.shared.ftp.presentation.FtpContent
import com.justparokq.homeftp.shared.login.presentation.LoginContent
import com.justparokq.homeftp.shared.root.presentation.component.RootComponent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
        Children(
            stack = component.stack,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation(fade() + scale())
        ) {
            when (val instance = it.instance) {
                is RootComponent.Child.Login -> LoginContent(component = instance.component)
                is RootComponent.Child.Main -> MainContent(component = instance.component)
                is RootComponent.Child.Ftp -> FtpContent(component = instance.component)
            }
        }
    }
}
