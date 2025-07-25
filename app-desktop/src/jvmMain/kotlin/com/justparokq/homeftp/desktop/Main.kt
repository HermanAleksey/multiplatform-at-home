package com.justparokq.homeftp.desktop

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.justparokq.homeftp.shared.navigation.acrhitecture.IInputActionDelegate
import com.justparokq.homeftp.shared.root.presentation.RootContent
import com.justparokq.homeftp.shared.root.presentation.component.DefaultRootComponent
import com.justparokq.homeftp.shared.root.presentation.startKoinImpl
import com.justparokq.homeftp.shared.utils.ContextFactory
import com.justparokq.homeftp.theme.AppTheme
import java.io.File

@Suppress("unused")
fun deleteSettingsDb() {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "setting_new.db")
    println("DB path: ${dbFile.absolutePath}")
    if (dbFile.exists()) {
        val deleted = dbFile.delete()
        println("DB deleted: $deleted")
    } else {
        println("DB file does not exist")
    }
}

fun main() {
//     deleteSettingsDb()

    val lifecycle = LifecycleRegistry()
    val contextFactory = ContextFactory()
    startKoinImpl(contextFactory)

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
            title = "KMPet",
            resizable = false,
            onKeyEvent = processKeyEvent(
                inputActionDelegate = rootComponent as IInputActionDelegate,
                exitApplication = ::exitApplication
            ),
            icon = getIconBitmapPainter("app_icon.png")
        ) {
            AppTheme {
                RootContent(
                    component = rootComponent,
                    contextFactory = contextFactory,
                )
            }
        }
    }
}

private fun processKeyEvent(
    inputActionDelegate: IInputActionDelegate,
    exitApplication: () -> Unit
): (KeyEvent) -> Boolean = { keyEvent ->
    when {
        keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Escape -> {
            if (!inputActionDelegate.processBackInput()) {
                exitApplication()
            }
            true
        }

        keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.Enter -> {
            inputActionDelegate.processNextInput()
            true
        }

        else -> false
    }
}
