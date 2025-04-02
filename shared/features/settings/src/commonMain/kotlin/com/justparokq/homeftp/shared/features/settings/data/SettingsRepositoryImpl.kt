package com.justparokq.homeftp.shared.features.settings.data

import com.justparokq.homeftp.shared.features.settings.domain.SettingModel
import com.justparokq.homeftp.shared.features.settings.domain.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// todo impl
class SettingsRepositoryImpl : SettingsRepository {

    override suspend fun getSetting(name: String): SettingModel? {
        return null
    }

    override suspend fun updateSetting(name: String, newValue: SettingModel.Value): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun observeSettings(): Flow<List<SettingModel>> {
        return flow { }
    }

    override suspend fun resetToDefaults(): Result<Unit> {
        return Result.success(Unit)
    }
}