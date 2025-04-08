package com.justparokq.homeftp.shared.features.settings.presentation.component

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.features.settings.presentation.model.SettingsScreenModel
import com.justpoarokq.shared.core.base_database.model.SettingModel

object PreviewSettingsComponent : SettingsComponent {
    override val state: Value<SettingsScreenModel>
        get() = TODO("Not yet implemented")

    override fun onSettingToggle(settingModel: SettingModel) {
    }

    override fun onResetClicked() {
    }

    override fun onDatabaseInitialized() {
    }
}
