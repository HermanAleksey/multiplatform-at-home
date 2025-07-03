package com.justparokq.homeftp.shared.root.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.justparokq.homeftp.shared.root.presentation.component.RootComponent
import com.justparokq.homeftp.shared.utils.ContextFactory
import com.justparokq.homeftp.theme.AppTheme
import platform.UIKit.UIViewController

fun rootViewController(rootComponent: RootComponent): UIViewController {
    return ComposeUIViewController {
        AppTheme {
            RootContent(
                component = rootComponent,
                contextFactory = ContextFactory(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}