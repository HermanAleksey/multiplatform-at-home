package com.justparokq.homeftp.shared.login.presentation.di

import com.justparokq.homeftp.shared.login.network.LoginNetworkComponent
import com.justparokq.homeftp.shared.login.presentation.component.DefaultLoginComponent
import com.justparokq.homeftp.shared.login.presentation.component.LoginComponent
import org.koin.dsl.bind
import org.koin.dsl.module

val loginModule = module {
    factory<LoginNetworkComponent> { LoginNetworkComponent() }
    factory<LoginComponent> {
        DefaultLoginComponent(
            componentContext = get(),
            loginNetworkComponent = get(),
            featureNavigator = get(),
        )
    } bind LoginComponent::class
}