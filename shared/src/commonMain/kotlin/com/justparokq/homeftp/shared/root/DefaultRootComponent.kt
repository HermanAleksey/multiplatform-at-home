package com.justparokq.homeftp.shared.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.ftp.presentation.DefaultFtpExplorerComponent
import com.justparokq.homeftp.shared.ftp.presentation.FtpExplorerComponent
import com.justparokq.homeftp.shared.login.presentation.DefaultLoginComponent
import com.justparokq.homeftp.shared.login.presentation.LoginComponent
import com.justparokq.homeftp.shared.main.presentation.DefaultMainComponent
import com.justparokq.homeftp.shared.main.presentation.MainComponent
import com.justparokq.homeftp.shared.root.RootComponent.Child
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val featureNavigator: FeatureNavigator = FeatureNavigatorImpl()
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Login,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, childComponentContext: ComponentContext): Child =
        when (config) {
            is Config.Login -> Child.Login(loginComponent(childComponentContext))
            is Config.Main -> Child.Main(mainComponent(childComponentContext))
            is Config.Ftp -> Child.Ftp(ftpComponent(childComponentContext))
        }

    private fun loginComponent(componentContext: ComponentContext): LoginComponent =
        DefaultLoginComponent(
            componentContext = componentContext,
            navigateToMainPage = { navigation.push(Config.Main) },
//            loginNetworkComponent = loginNetworkComponent
        )

    private fun mainComponent(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            featureNavigator = featureNavigator,
        )

    private fun ftpComponent(componentContext: ComponentContext): FtpExplorerComponent =
        DefaultFtpExplorerComponent(
            componentContext = componentContext,
//            onShowWelcome = { navigation.push(Config.Login) },
        )

    // todo create enum of Features. Method `fun navigateTo(feature: FeatureEnum)` that we can provide
    // into features

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    interface FeatureNavigator {
        fun navigate(projectFeature: ProjectFeature)
    }

    inner class FeatureNavigatorImpl: FeatureNavigator {

        override fun navigate(projectFeature: ProjectFeature) {
            when (projectFeature) {
                ProjectFeature.LOGIN -> navigation.push(Config.Login)
                ProjectFeature.MAIN -> navigation.push(Config.Main)
                ProjectFeature.FTP -> navigation.push(Config.Ftp)
            }
        }
    }

    @Serializable
    private sealed interface Config {

        @Serializable
        data object Login : Config

        @Serializable
        data object Main : Config

        @Serializable
        data object Ftp : Config
    }
}
