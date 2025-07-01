package com.justpoarokq.shared.core.base_database.repository

import com.justpoarokq.shared.core.base_database.api.SettingModel
import com.justpoarokq.shared.core.base_database.api.SettingsRepository
import com.justpoarokq.shared.core.base_database.dao.BooleanSettingDao
import com.justpoarokq.shared.core.base_database.dao.StringSettingDao
import com.justpoarokq.shared.core.base_database.mapper.SettingMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Implementation of [SettingsRepository] that manages settings using Room database.
 * Provides thread-safe operations with mutex protection for database consistency.
 */
internal class SettingsRepositoryImpl(
    private val boolDao: BooleanSettingDao,
    private val strDao: StringSettingDao,
    private val mapper: SettingMapper,
) : SettingsRepository {

    private val settingsFlow = MutableStateFlow<List<SettingModel>>(emptyList())

    private val mutex = Mutex()

    override suspend fun getBoolSettingValue(name: String): Boolean? = mutex.withLock {
        require(name.isNotBlank()) { "Setting name cannot be blank" }
        boolDao.getSettingByName(name)?.value
    }

    override suspend fun getStringSettingValue(name: String): String? = mutex.withLock {
        require(name.isNotBlank()) { "Setting name cannot be blank" }
        strDao.getSettingByName(name)?.value
    }

    override suspend fun updateSetting(name: String, setting: SettingModel) = mutex.withLock {
        require(name.isNotBlank()) { "Setting name cannot be blank" }
        require(setting.name == name) { "Setting name mismatch: expected '$name', got '${setting.name}'" }

        when (setting.value) {
            is SettingModel.Value.Boolean -> {
                val mappedValue = mapper.toBooleanSettingEntity(setting)
                boolDao.insert(mappedValue)
            }
            is SettingModel.Value.String -> {
                val mappedValue = mapper.toStringSettingEntity(setting)
                strDao.insert(mappedValue)
            }
        }
        settingsFlow.emit(getCombinedData())
    }

    override suspend fun observeSettings(): Flow<List<SettingModel>> = mutex.withLock {
        settingsFlow.emit(getCombinedData())
        return settingsFlow.asSharedFlow()
    }

    override suspend fun observeFeatures(): Flow<List<SettingModel>> = mutex.withLock {
        settingsFlow.emit(getCombinedData())
        return settingsFlow.asSharedFlow().map { allSettings ->
            allSettings.filter { it.category == SettingModel.Category.Feature }
        }
    }

    override suspend fun resetToDefaults() = mutex.withLock {
        boolDao.clearAll()
        strDao.clearAll()
        settingsFlow.emit(emptyList())
    }

    override suspend fun deleteSetting(name: String): Boolean = mutex.withLock {
        require(name.isNotBlank()) { "Setting name cannot be blank" }

        val boolDeleted = boolDao.deleteByName(name) > 0
        val stringDeleted = strDao.deleteByName(name) > 0

        if (boolDeleted || stringDeleted) {
            settingsFlow.emit(getCombinedData())
        }

        boolDeleted || stringDeleted
    }

    private suspend fun getCombinedData(): List<SettingModel> {
        val boolSettings = boolDao.getAllSettings().map { mapper.toSettingModel(it) }
        val stringSettings = strDao.getAllSettings().map { mapper.toSettingModel(it) }
        return boolSettings + stringSettings
    }
}