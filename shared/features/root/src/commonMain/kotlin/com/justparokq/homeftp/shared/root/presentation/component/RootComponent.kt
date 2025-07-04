package com.justparokq.homeftp.shared.root.presentation.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.features.settings.api.SettingsComponent
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponent
import com.justparokq.homeftp.shared.login.api.LoginComponent
import com.justparokq.homeftp.shared.main.api.MainComponent


interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    /**
     * Pops one node from the backstack.
     *
     * @return true if a node was popped, false if it was the last screen in the stack.
     */
    fun onBackClicked(): Boolean

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class Login(val component: LoginComponent) : Child()
        class Main(val component: MainComponent) : Child()
        class Ftp(val component: FtpExplorerComponent) : Child()
        class Settings(val component: SettingsComponent) : Child()
    }
}
