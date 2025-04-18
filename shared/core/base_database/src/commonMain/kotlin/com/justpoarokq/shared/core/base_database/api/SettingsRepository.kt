package com.justpoarokq.shared.core.base_database.api

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    // some kind of feature-toggle
    suspend fun getBoolSettingValue(name: String): Boolean?

    suspend fun updateSetting(name: String, setting: SettingModel)

    suspend fun observeSettings(): Flow<List<SettingModel>>

    suspend fun resetToDefaults()
}