package com.justparokq.homeftp.shared.features.settings.api

import com.justparokq.homeftp.shared.features.settings.presentation.component.DefaultSettingsComponent
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val settingsModule = module {
    factoryOf(::DefaultSettingsComponent) bind SettingsComponent::class
}