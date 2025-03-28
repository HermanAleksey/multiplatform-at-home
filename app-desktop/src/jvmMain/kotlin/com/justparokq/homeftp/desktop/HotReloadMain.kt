package com.justparokq.homeftp.desktop

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import com.justparokq.homeftp.theme.AppTheme
import org.jetbrains.compose.reload.DevelopmentEntryPoint

fun main() {
    singleWindowApplication(
        title = "My CHR App",
        state = WindowState(width = 800.dp, height = 800.dp),
        alwaysOnTop = true
    ) {
        DevelopmentEntryPoint {
            MainPage()
        }
    }
}

@Composable
fun MainPage() {
    AppTheme {
        Text("Hello!")
    }
}