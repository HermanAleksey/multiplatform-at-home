package com.justparokq.homeftp.shared.main.api

import com.justparokq.homeftp.shared.main.data.FeatureToggleRepositoryImpl
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModelMapper
import com.justparokq.homeftp.shared.main.domain.FeatureToggleRepository
import com.justparokq.homeftp.shared.main.presentation.component.DefaultMainComponent
import com.justpoarokq.shared.core.base_database.api.SettingsRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule = module {
    factory { FeatureParamsModelMapper() }
    factory<FeatureToggleRepository> { 
        FeatureToggleRepositoryImpl(
            settingsRepository = get<SettingsRepository>()
        ) 
    }
    factory<MainComponent> {
        DefaultMainComponent(
            componentContext = get(),
            featureNavigator = get(),
            featureParamsModelMapper = get(),
            featureToggleRepository = get(),
        )
    } bind MainComponent::class
}