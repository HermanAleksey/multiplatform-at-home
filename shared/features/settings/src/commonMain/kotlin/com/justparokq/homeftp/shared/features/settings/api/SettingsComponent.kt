package com.justparokq.homeftp.shared.features.settings.api

import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponentIntent
import com.justparokq.homeftp.shared.navigation.acrhitecture.BaseComponentState
import com.justpoarokq.shared.core.base_database.api.SettingModel

interface SettingsComponent : BaseComponent<SettingsComponentState, SettingsComponentIntent>

sealed interface SettingsComponentState : BaseComponentState
internal data class SettingsScreenModel(
    val networkSettings: List<SettingModel> = emptyList(),
    val featureSettings: List<SettingModel> = emptyList(),
) : SettingsComponentState


sealed interface SettingsComponentIntent : BaseComponentIntent
internal data class OnSettingsToggle(val settingModel: SettingModel) : SettingsComponentIntent
internal data object OnResetClicked : SettingsComponentIntent