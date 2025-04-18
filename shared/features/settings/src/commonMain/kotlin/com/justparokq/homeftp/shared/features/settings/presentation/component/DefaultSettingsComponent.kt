package com.justparokq.homeftp.shared.features.settings.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.features.settings.data.DatabaseObject
import com.justparokq.homeftp.shared.features.settings.presentation.model.SettingsScreenModel
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import com.justpoarokq.shared.core.base_database.model.SettingModel
import com.justpoarokq.shared.core.base_database.repository.DatabaseServiceLocator
import com.justpoarokq.shared.core.base_database.repository.SettingsRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DefaultSettingsComponent(
    private val serviceLocator: DatabaseServiceLocator,
    componentContext: ComponentContext,
) : SettingsComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private var settingsRepository: SettingsRepository? = null

    private val _state = MutableValue(SettingsScreenModel(emptyList()))
    override val state: Value<SettingsScreenModel> = _state

    override fun onSettingToggle(settingModel: SettingModel) {
        coroutineScope.launch {
            settingsRepository?.updateSetting(settingModel.name, settingModel)
        }
    }

    override fun onResetClicked() {
        coroutineScope.launch {
            settingsRepository?.resetToDefaults()
        }
    }

    override fun onDatabaseInitialized() {
        val db = DatabaseObject.settings()
        settingsRepository = serviceLocator.provide(db)
        loadData()
    }

    private fun loadData() {
        coroutineScope.launch {
            val repo = settingsRepository ?: return@launch
            repo.observeSettings()
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
