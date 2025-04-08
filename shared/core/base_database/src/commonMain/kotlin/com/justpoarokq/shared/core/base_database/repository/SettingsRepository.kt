package com.justpoarokq.shared.core.base_database.repository

import com.justpoarokq.shared.core.base_database.dao.BooleanSettingDao
import com.justpoarokq.shared.core.base_database.dao.StringSettingDao
import com.justpoarokq.shared.core.base_database.database.SettingDatabase
import com.justpoarokq.shared.core.base_database.mapper.SettingMapper
import com.justpoarokq.shared.core.base_database.model.SettingModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

// todo replace with some fancy di
object DatabaseServiceLocator {

    fun provide(database: SettingDatabase): SettingsRepository {
        return SettingsRepositoryImpl(
            database.booleanSettingDao(),
            database.stringSettingDao(),
            SettingMapper()
        )
    }
}

interface SettingsRepository {

    // some kind of feature-toggle
    suspend fun getBoolSettingValue(name: String): Boolean?

    suspend fun updateSetting(name: String, setting: SettingModel)

    suspend fun observeSettings(): Flow<List<SettingModel>>

    suspend fun resetToDefaults()
}

internal class SettingsRepositoryImpl(
    private val boolDao: BooleanSettingDao,
    private val strDao: StringSettingDao,
    private val mapper: SettingMapper,
) : SettingsRepository {

    private val msf = MutableSharedFlow<List<SettingModel>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    private val mutex = Mutex()

    override suspend fun getBoolSettingValue(name: String): Boolean? {
        mutex.withLock {
            return boolDao.getSettingByName(name)?.value
        }
    }

    override suspend fun updateSetting(name: String, setting: SettingModel): Unit = mutex.withLock {
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
        msf.emit(getCombinedData())
    }

    override suspend fun observeSettings(): Flow<List<SettingModel>> = mutex.withLock {
        msf.emit(getCombinedData())
        return msf.asSharedFlow()
    }

    private suspend fun getCombinedData(): List<SettingModel> {
        val boolSettings = boolDao.getAllSettings().map { mapper.toSettingModel(it) }.toSet()
        val stringSettings = strDao.getAllSettings().map { mapper.toSettingModel(it) }.toSet()
        return (boolSettings + stringSettings).toList()
    }

    override suspend fun resetToDefaults(): Unit = mutex.withLock {
        boolDao.clearAll()
        strDao.clearAll()
    }
}