package com.justparokq.homeftp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.justparokq.homeftp.shared.root.presentation.RootContent
import com.justparokq.homeftp.shared.root.presentation.component.DefaultRootComponent
import com.justparokq.homeftp.shared.root.presentation.startKoinImpl
import com.justparokq.homeftp.shared.utils.ContextFactory
import com.justparokq.homeftp.theme.AndroidAppTheme
import io.github.vinceglb.filekit.core.FileKit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FileKit.init(this)

        val contextFactory = ContextFactory(this)
        startKoinImpl(contextFactory)

        val root = DefaultRootComponent(componentContext = defaultComponentContext())
        setContent {
            AndroidAppTheme {
                RootContent(component = root, contextFactory = ContextFactory(this))
            }
        }
    }
}
