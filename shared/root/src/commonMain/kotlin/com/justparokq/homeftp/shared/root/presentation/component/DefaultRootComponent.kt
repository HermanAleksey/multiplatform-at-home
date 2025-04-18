package com.justparokq.homeftp.shared.root.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.features.settings.presentation.component.DefaultSettingsComponent
import com.justparokq.homeftp.shared.features.settings.presentation.component.SettingsComponent
import com.justparokq.homeftp.shared.ftp.presentation.component.DefaultFtpExplorerComponent
import com.justparokq.homeftp.shared.ftp.presentation.component.FtpExplorerComponent
import com.justparokq.homeftp.shared.login.api.LoginComponent
import com.justparokq.homeftp.shared.main.api.MainComponent
import com.justparokq.homeftp.shared.root.presentation.component.RootComponent.Child
import com.justparokq.homeftp.shared.root.presentation.koinApp
import com.justparokq.homeftp.shared.root.presentation.navigation.Config
import com.justparokq.homeftp.shared.root.presentation.navigation.FeatureNavigatorImpl
import com.justpoarokq.shared.core.base_database.repository.DatabaseServiceLocator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Config>()
    private val featureNavigator: FeatureNavigator = FeatureNavigatorImpl(navigation)

    init {
        koinApp
    }

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Login,
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, childComponentContext: ComponentContext): Child {
        return when (config) {
            is Config.Login -> Child.Login(loginComponent(childComponentContext))
            is Config.Main -> Child.Main(mainComponent(childComponentContext))
            is Config.Ftp -> Child.Ftp(ftpComponent(childComponentContext))
            is Config.Settings -> Child.Settings(settingsComponent(childComponentContext))
        }
    }

    private fun loginComponent(componentContext: ComponentContext): LoginComponent {
        val comp: LoginComponent by inject { parametersOf(componentContext, featureNavigator) }
        return comp
    }

    private fun mainComponent(componentContext: ComponentContext): MainComponent {
        val comp: MainComponent by inject { parametersOf(componentContext, featureNavigator) }
        return comp
    }

    private fun ftpComponent(componentContext: ComponentContext): FtpExplorerComponent {
        return DefaultFtpExplorerComponent(
            componentContext = componentContext,
        )
    }

    private fun settingsComponent(componentContext: ComponentContext): SettingsComponent {
//        val component = koinApp.koin.get<DefaultSettingsComponent> {
//            parametersOf(componentContext)
//        }
        return DefaultSettingsComponent(
            serviceLocator = DatabaseServiceLocator,
            componentContext = componentContext
        )
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }
}
