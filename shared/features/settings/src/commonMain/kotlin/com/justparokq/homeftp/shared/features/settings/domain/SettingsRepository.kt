package com.justparokq.homeftp.shared.features.settings.domain

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    suspend fun getSetting(name: String): SettingModel?

    suspend fun updateSetting(name: String, newValue: SettingModel.Value): Result<Unit>

    suspend fun observeSettings(): Flow<List<SettingModel>>

    suspend fun resetToDefaults(): Result<Unit>
}