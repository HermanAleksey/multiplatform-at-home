package com.justparokq.homeftp.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.justparokq.homeftp.shared.root.presentation.RootContent
import com.justparokq.homeftp.shared.root.presentation.component.DefaultRootComponent
import com.justparokq.homeftp.shared.utils.ContextFactory
import com.justparokq.homeftp.theme.AppTheme

fun main() {

    val lifecycle = LifecycleRegistry()

    val rootComponent = runOnUiThread {
        DefaultRootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
        )
    }

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "My Application",
            resizable = false,
        ) {
            AppTheme {
                RootContent(component = rootComponent, contextFactory = ContextFactory())
            }
        }
    }
}
