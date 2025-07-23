package com.justparokq.homeftp.shared.features.settings.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.features.settings.api.OnResetClicked
import com.justparokq.homeftp.shared.features.settings.api.OnSettingsToggle
import com.justparokq.homeftp.shared.features.settings.api.SettingsComponent
import com.justparokq.homeftp.shared.features.settings.api.SettingsComponentIntent
import com.justparokq.homeftp.shared.features.settings.api.SettingsScreenModel
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import com.justpoarokq.shared.core.base_database.api.SettingModel
import com.justpoarokq.shared.core.base_database.api.SettingsRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class DefaultSettingsComponent(
    private val settingsRepository: SettingsRepository,
    componentContext: ComponentContext,
) : SettingsComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    private val _state = MutableValue(SettingsScreenModel())
    override val state: Value<SettingsScreenModel> = _state

    override fun processIntent(intent: SettingsComponentIntent) {
        when (intent) {
            is OnSettingsToggle -> onSettingToggle(intent.settingModel)
            OnResetClicked -> onResetClicked()
        }
    }

    init {
        loadData()
    }

    private fun onSettingToggle(settingModel: SettingModel) {
        coroutineScope.launch {
            settingsRepository.updateSetting(settingModel.name, settingModel)
        }
    }

    private fun onResetClicked() {
        coroutineScope.launch {
            settingsRepository.resetToDefaults()
        }
    }

    private fun loadData() {
        coroutineScope.launch {
            settingsRepository.observeSettings()
                .map { settingsList ->
                    val settingsMap = settingsList.groupBy { it.category }
                    SettingsScreenModel(
                        networkSettings = settingsMap[SettingModel.Category.Network] ?: emptyList(),
                        featureSettings = settingsMap[SettingModel.Category.Feature] ?: emptyList()
                    )
                }
                .collect { newModel ->
                    _state.update { newModel }
                }
        }
    }
}
