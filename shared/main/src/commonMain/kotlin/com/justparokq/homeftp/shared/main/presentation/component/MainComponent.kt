package com.justparokq.homeftp.shared.main.presentation.component

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModel
import com.justparokq.homeftp.shared.main.domain.MainScreenModel

interface MainComponent {

    val state: Value<MainScreenModel>

    fun onInitialize()

    fun onFeatureClicked(feature: FeatureParamsModel)
}
