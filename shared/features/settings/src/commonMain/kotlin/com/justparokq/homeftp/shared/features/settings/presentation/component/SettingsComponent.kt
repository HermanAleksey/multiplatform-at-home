package com.justparokq.homeftp.shared.features.settings.presentation.component

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.features.settings.presentation.model.SettingsScreenModel
import com.justpoarokq.shared.core.base_database.model.SettingModel

interface SettingsComponent {

    val state: Value<SettingsScreenModel>

    fun onSettingChanged(settingModel: SettingModel)

    fun onResetClicked()

    fun onDatabaseInitialized()
}
