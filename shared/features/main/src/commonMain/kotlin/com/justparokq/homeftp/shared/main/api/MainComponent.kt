package com.justparokq.homeftp.shared.main.api

import com.justparokq.homeftp.shared.main.domain.FeatureParamsModel
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponentIntent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponentState

interface MainComponent : BaseComponent<MainComponentState, MainComponentIntent>


sealed interface MainComponentState : BaseComponentState

internal data class Default(
    val features: List<FeatureParamsModel> = emptyList(),
) : MainComponentState


sealed interface MainComponentIntent : BaseComponentIntent

internal data object Init : MainComponentIntent

internal data class OnFeatureClicked(
    val feature: FeatureParamsModel
) : MainComponentIntent
