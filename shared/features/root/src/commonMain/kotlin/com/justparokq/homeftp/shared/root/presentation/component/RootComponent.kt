package com.justparokq.homeftp.shared.root.presentation.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.justparokq.homefpt.shared.core.network.coil.RequestHeaderInterceptor
import com.justparokq.homeftp.shared.features.settings.api.SettingsComponent
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponent
import com.justparokq.homeftp.shared.login.api.LoginComponent
import com.justparokq.homeftp.shared.main.api.MainComponent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponent


interface RootComponent: BackHandlerOwner {

    val stack: Value<ChildStack<*, Child>>
    val imageRequestInterceptor: RequestHeaderInterceptor

    /**
     * Pops one node from the backstack.
     *
     * @return true if a node was popped, false if it was the last screen in the stack.
     */
    fun onBackClicked(): Boolean

    fun onBackClicked(toIndex: Int)

    sealed interface Child {

        val component: BaseComponent<*, *>

        class Login(override val component: LoginComponent) : Child
        class Main(override val component: MainComponent) : Child
        class Ftp(override val component: FtpExplorerComponent) : Child
        class Settings(override val component: SettingsComponent) : Child
    }
}
