package com.justparokq.homeftp.shared.main.data

import com.justparokq.homeftp.shared.main.domain.FeatureToggle
import com.justparokq.homeftp.shared.main.domain.FeatureToggleRepository
import com.justpoarokq.shared.core.base_database.api.SettingsRepository
import com.justpoarokq.shared.core.base_database.api.SettingModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.justparokq.homeftp.shared.core.feature_key.FeatureKey

internal class FeatureToggleRepositoryImpl(
    private val settingsRepository: SettingsRepository
) : FeatureToggleRepository {

    override suspend fun getAll(): Flow<List<FeatureToggle>> {
        return settingsRepository.observeFeatures().map { featureSettings ->
            featureSettings.mapNotNull { setting ->
                val key = FeatureKey.fromKey(setting.name)
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