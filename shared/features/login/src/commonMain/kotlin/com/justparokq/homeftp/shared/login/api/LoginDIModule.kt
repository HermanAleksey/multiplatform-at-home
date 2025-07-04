package com.justparokq.homeftp.shared.login.api

import com.justparokq.homeftp.shared.login.network.LoginNetworkComponent
import com.justparokq.homeftp.shared.login.network.LoginRepository
import com.justparokq.homeftp.shared.login.network.LoginRepositoryImpl
import com.justparokq.homeftp.shared.login.presentation.component.DefaultLoginComponent
import networkCoreModule
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModule = module {
    includes(networkCoreModule)
    factory<LoginNetworkComponent> { LoginNetworkComponent(get()) }
    factory<LoginRepository> {
        LoginRepositoryImpl(networkSettingsInteractor = get(), loginNetworkComponent = get())
    }
    factory<LoginComponent> {
        DefaultLoginComponent(
            componentContext = get(),
            loginRepository = get(),
            featureNavigator = get(),
            settingsInteractor = get(),
        )
    } bind LoginComponent::class
}