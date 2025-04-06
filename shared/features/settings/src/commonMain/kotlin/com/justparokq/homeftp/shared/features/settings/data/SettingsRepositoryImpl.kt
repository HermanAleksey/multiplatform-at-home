package com.justparokq.homeftp.shared.features.settings.data

import com.justparokq.homeftp.shared.features.settings.domain.SettingModel
import com.justparokq.homeftp.shared.features.settings.domain.SettingModel.Value
import com.justparokq.homeftp.shared.features.settings.domain.SettingsRepository
import com.justpoarokq.shared.core.base_database.dao.SettingDao
import com.justpoarokq.shared.core.base_database.entity.SettingEntity
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SettingsRepositoryImpl(
    private val dao: SettingDao
) : SettingsRepository {

    private val msf = MutableSharedFlow<List<SettingModel>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val mutex = Mutex()

    override suspend fun getSetting(name: String): SettingModel? {
        // todo impl
        return null
    }

    override suspend fun updateSetting(name: String, newValue: Value): Result<Unit> {
        mutex.withLock {
            val updatedSetting = dao.getSettingByName(name)?.copy(value = newValue.toString())
                ?: return Result.failure(Exception("Couldn't find setting with this name"))
            dao.insert(updatedSetting)
            msf.emit(dao.getAllSettings().mapDao())
            return Result.success(Unit)
        }
    }

    override suspend fun observeSettings(): Flow<List<SettingModel>> {
        msf.emit(dao.getAllSettings().mapDao())
        return msf.asSharedFlow()
    }

    override suspend fun resetToDefaults(): Result<Unit> {
        mutex.withLock {
            dao.getAllSettings()
            // todo impl?
            return Result.success(Unit)
        }
    }

    private fun List<SettingEntity>.mapDao(): List<SettingModel> {
        return map { it.mapDao() }
    }

    private fun SettingEntity.mapDao(): SettingModel {
        return SettingModel(
            name = name,
            description = description,
            category = SettingModel.Category.entries.find {
                it.name.equals(
                    category,
                    ignoreCase = true
                )
            } ?: error("Unknown category"),
            // todo null = false, fix wit custom mapping
            value = Value.Boolean(value.toBoolean())
        )
    }
}