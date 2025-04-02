package com.justparokq.homeftp.shared.features.settings.presentation.model

import com.justparokq.homeftp.shared.features.settings.domain.SettingModel

data class SettingsScreenModel(
    val networkSettings: List<SettingModel> = emptyList(),
    val featureSettings: List<SettingModel> = emptyList(),
)
