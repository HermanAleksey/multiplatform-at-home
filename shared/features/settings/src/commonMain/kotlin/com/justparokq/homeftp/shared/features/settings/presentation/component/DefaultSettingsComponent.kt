package com.justparokq.homeftp.shared.features.settings.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.features.settings.data.SettingsRepositoryImpl
import com.justparokq.homeftp.shared.features.settings.domain.SettingModel
import com.justparokq.homeftp.shared.features.settings.domain.SettingsRepository
import com.justparokq.homeftp.shared.features.settings.presentation.model.SettingsScreenModel
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DefaultSettingsComponent(
    private val settingsRepository: SettingsRepository = SettingsRepositoryImpl(),
    componentContext: ComponentContext,
) : SettingsComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    private val _state = MutableValue(SettingsScreenModel(emptyList()))
    override val state: Value<SettingsScreenModel> = _state

    init {
        coroutineScope.launch {
            settingsRepository.observeSettings()
                .map { settingsList ->
                    val settingsMap = settingsList.groupBy { it.category }
                    SettingsScreenModel(
                        networkSettings = settingsMap[SettingModel.Category.Network] ?: emptyList(),
                        featureSettings = settingsMap[SettingModel.Category.Features] ?: emptyList()
                    )
                }
                .collect {
                    _state.update { it }
                }
        }
    }

    override fun onSettingChanged(settingModel: SettingModel) {
        coroutineScope.launch {
            settingsRepository.updateSetting(settingModel.name, settingModel.value)
        }
    }

    override fun onResetClicked() {
        coroutineScope.launch {
            settingsRepository.resetToDefaults()
        }
    }
}
