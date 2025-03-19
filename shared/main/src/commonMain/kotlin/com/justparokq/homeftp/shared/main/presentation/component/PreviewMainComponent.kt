package com.justparokq.homeftp.shared.main.presentation.component

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModel
import com.justparokq.homeftp.shared.main.domain.MainScreenModel

object PreviewMainComponent : MainComponent {
    override val state: Value<MainScreenModel>
        get() = TODO("Not yet implemented")

    override fun onInitialize() {}

    override fun onFeatureClicked(feature: FeatureParamsModel) {
        TODO("Not yet implemented")
    }
}
