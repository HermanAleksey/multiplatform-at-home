package com.justparokq.homeftp.shared.main.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.feature.FeatureNavigator
import com.justparokq.homeftp.shared.main.data.FeatureToggleRepositoryImpl
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModel
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModelMapper
import com.justparokq.homeftp.shared.main.domain.FeatureToggleRepository
import com.justparokq.homeftp.shared.main.domain.MainScreenModel
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class DefaultMainComponent(
    componentContext: ComponentContext,
    private val featureParamsModelMapper: FeatureParamsModelMapper = FeatureParamsModelMapper(),
    private val featureToggleRepository: FeatureToggleRepository = FeatureToggleRepositoryImpl(),
    private val featureNavigator: FeatureNavigator,
) : MainComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    private val _state = MutableValue(MainScreenModel())
    override val state: Value<MainScreenModel> = _state

    init {
        onInitialize()
    }

    override fun onInitialize() {
        coroutineScope.launch(Dispatchers.IO) {
            val features = featureToggleRepository.getAll()
                .filter { it.isEnabled }
                .map { featureParamsModelMapper.map(it) }
            _state.update {
                it.copy(
                    features = features
                )
            }
        }
    }

    override fun onFeatureClicked(feature: FeatureParamsModel) {
        if (!feature.isEnabled) return
        featureNavigator.navigate(feature.feature)
    }
}
