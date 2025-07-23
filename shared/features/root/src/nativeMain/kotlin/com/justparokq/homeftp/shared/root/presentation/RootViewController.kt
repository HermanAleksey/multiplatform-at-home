package com.justparokq.homeftp.shared.root.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureIcon
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.justparokq.homeftp.shared.root.presentation.component.RootComponent
import com.justparokq.homeftp.shared.utils.ContextFactory
import com.justparokq.homeftp.theme.AppTheme
import platform.UIKit.UIViewController

@OptIn(ExperimentalDecomposeApi::class)
fun rootViewController(rootComponent: RootComponent): UIViewController {
    val backDispatcher = BackDispatcher()
    backDispatcher.register(object : BackCallback() {
        override fun onBack() {
            rootComponent.onBackClicked()
        }
    })
    val composeViewController = ComposeUIViewController {
        AppTheme {
            PredictiveBackGestureOverlay(
                backDispatcher = backDispatcher,
                backIcon = { progress, _ ->
                    PredictiveBackGestureIcon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        progress = progress,
                    )
                },
                modifier = Modifier.fillMaxSize(),
            ) {
                RootContent(
                    component = rootComponent,
                    contextFactory = ContextFactory(),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
    return composeViewController
}