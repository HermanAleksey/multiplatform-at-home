package com.justparokq.homeftp.shared.root.presentation.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.features.settings.presentation.component.SettingsComponent
import com.justparokq.homeftp.shared.ftp.presentation.component.FtpExplorerComponent
import com.justparokq.homeftp.shared.login.presentation.component.LoginComponent
import com.justparokq.homeftp.shared.main.api.MainComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class Login(val component: LoginComponent) : Child()
        class Main(val component: MainComponent) : Child()
        class Ftp(val component: FtpExplorerComponent) : Child()
        class Settings(val component: SettingsComponent) : Child()
    }
}
