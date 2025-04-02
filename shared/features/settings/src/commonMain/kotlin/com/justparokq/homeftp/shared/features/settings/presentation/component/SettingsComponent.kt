package com.justparokq.homeftp.shared.features.settings.presentation.component

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.features.settings.domain.SettingModel
import com.justparokq.homeftp.shared.features.settings.presentation.model.SettingsScreenModel

interface SettingsComponent {

    val state: Value<SettingsScreenModel>

    fun onSettingChanged(settingModel: SettingModel)

    fun onResetClicked()
}
