package com.justparokq.homeftp.shared.main.presentation.component

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.main.api.Default
import com.justparokq.homeftp.shared.main.api.MainComponent
import com.justparokq.homeftp.shared.main.api.MainComponentIntent
import com.justparokq.homeftp.shared.main.api.MainComponentState
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModel

internal object PreviewMainComponent : MainComponent {
    override val state: Value<MainComponentState>
        get() = MutableValue(Default())

    override fun processAction(intent: MainComponentIntent) {
//        TODO("Not yet implemented")
    }
}
