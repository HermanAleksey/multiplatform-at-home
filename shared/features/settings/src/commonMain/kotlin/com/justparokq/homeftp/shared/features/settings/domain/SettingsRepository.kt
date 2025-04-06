package com.justparokq.homeftp.shared.features.settings.domain

import com.justparokq.homeftp.shared.features.settings.data.SettingsRepositoryImpl
import com.justpoarokq.shared.core.base_database.database.SettingDatabase
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun getSetting(name: String): SettingModel?

    suspend fun updateSetting(name: String, newValue: SettingModel.Value): Result<Unit>

    suspend fun observeSettings(): Flow<List<SettingModel>>

    suspend fun resetToDefaults(): Result<Unit>

// todo di
    object Provider {

        fun provide(database: SettingDatabase): SettingsRepository {
            return SettingsRepositoryImpl(database.settingDao())
        }
    }
}