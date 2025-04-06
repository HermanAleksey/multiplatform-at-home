package com.justparokq.homeftp.shared.features.settings.presentation.component

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.features.settings.domain.SettingModel
import com.justparokq.homeftp.shared.features.settings.presentation.model.SettingsScreenModel

object PreviewSettingsComponent : SettingsComponent {
    override val state: Value<SettingsScreenModel>
        get() = TODO("Not yet implemented")

    override fun onSettingChanged(settingModel: SettingModel) {
    }

    override fun onResetClicked() {
    }

    override fun onDatabaseInitialized() {
    }
}
