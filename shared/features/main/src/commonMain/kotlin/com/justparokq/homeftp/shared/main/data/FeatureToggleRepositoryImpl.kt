package com.justparokq.homeftp.shared.main.data

import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justparokq.homeftp.shared.main.domain.FeatureToggle
import com.justparokq.homeftp.shared.main.domain.FeatureToggleRepository
import com.justpoarokq.shared.core.base_database.api.SettingModel
import com.justpoarokq.shared.core.base_database.api.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class FeatureToggleRepositoryImpl(
    private val settingsRepository: SettingsRepository
) : FeatureToggleRepository {

    override suspend fun getAll(): Flow<List<FeatureToggle>> {
        return settingsRepository.observeFeatures().map { featureSettings ->
            featureSettings.mapNotNull { setting ->
                val key = Setting.fromKey(setting.name)
                if (key != null) {
                    FeatureToggle(
                        key = key,
                        isEnabled = when (setting.value) {
                            is SettingModel.Value.Boolean -> (setting.value as SettingModel.Value.Boolean).value
                            is SettingModel.Value.String -> false
                        }
                    )
                } else null
            }
        }
    }
}