package com.justparokq.homeftp.shared.main.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.main.api.Default
import com.justparokq.homeftp.shared.main.api.OnFeatureClicked
import com.justparokq.homeftp.shared.main.api.Init
import com.justparokq.homeftp.shared.main.api.MainComponent
import com.justparokq.homeftp.shared.main.api.MainComponentIntent
import com.justparokq.homeftp.shared.main.api.MainComponentState
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModel
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModelMapper
import com.justparokq.homeftp.shared.main.domain.FeatureToggleRepository
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

internal class DefaultMainComponent(
    componentContext: ComponentContext,
    private val featureParamsModelMapper: FeatureParamsModelMapper,
    private val featureToggleRepository: FeatureToggleRepository,
    private val featureNavigator: FeatureNavigator,
) : MainComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    private val _state = MutableValue(Default())
    override val state: Value<MainComponentState> = _state

    init {
        onInitialize()
    }

    override fun processAction(intent: MainComponentIntent) {
        when (intent) {
            Init -> onInitialize()
            is OnFeatureClicked -> onFeatureClicked(intent.feature)
        }
    }

    private fun onInitialize() {
        coroutineScope.launch(Dispatchers.IO) {
            val features = featureToggleRepository.getAll()
                .filter { it.isEnabled }
                .map { featureParamsModelMapper.map(it) }
            _state.update {
                it.copy(features = features)
            }
        }
    }

    private fun onFeatureClicked(feature: FeatureParamsModel) {
        if (!feature.isEnabled) return
        featureNavigator.navigate(feature.feature)
    }
}
