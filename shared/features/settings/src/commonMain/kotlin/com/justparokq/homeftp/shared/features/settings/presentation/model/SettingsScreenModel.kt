package com.justparokq.homeftp.shared.features.settings.presentation.model

import com.justpoarokq.shared.core.base_database.model.SettingModel

data class SettingsScreenModel(
    val networkSettings: List<SettingModel> = emptyList(),
    val featureSettings: List<SettingModel> = emptyList(),
)
