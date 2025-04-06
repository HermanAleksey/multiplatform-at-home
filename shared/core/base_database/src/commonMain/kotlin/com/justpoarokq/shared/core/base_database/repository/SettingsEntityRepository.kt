//package com.justpoarokq.shared.core.base_database.repository
//
//import com.justpoarokq.shared.core.base_database.dao.SettingDao
//import com.justpoarokq.shared.core.base_database.database.SettingDatabase
//import com.justpoarokq.shared.core.base_database.entity.SettingEntity
//import kotlinx.coroutines.channels.BufferOverflow
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.asSharedFlow
//import kotlinx.coroutines.sync.Mutex
//import kotlinx.coroutines.sync.withLock
//
//interface SettingsEntityRepository {
//
//    suspend fun getSetting(name: String): SettingEntity?
//
//    suspend fun updateSetting(obj: SettingEntity)
//
//    suspend fun observeSettings(): Flow<List<SettingEntity>>
//
//    suspend fun resetToDefaults()
//
//    // todo di
//    object Provider {
//
//        fun provide(database: SettingDatabase): SettingsEntityRepository {
//            return SettingsEntityRepositoryImpl(database.settingDao())
//        }
//    }
//}
//
//class SettingsEntityRepositoryImpl(
//    private val dao: SettingDao
//) : SettingsEntityRepository {
//
//    private val msf = MutableSharedFlow<List<SettingEntity>>(
//        replay = 1,
//        onBufferOverflow = BufferOverflow.DROP_OLDEST
//    )
//    private val mutex = Mutex()
//
//    override suspend fun getSetting(name: String): SettingEntity? {
//        // todo impl
//        return null
//    }
//
//    override suspend fun updateSetting(obj: SettingEntity) {
//        mutex.withLock {
//            val updatedSetting = dao.getSettingByName(name)?.copy(value = newValue.toString())
//                ?: return Result.failure(Exception("Couldn't find setting with this name"))
//            dao.insert(updatedSetting)
//            msf.emit(dao.getAllSettings().mapDao())
//            return Result.success(Unit)
//        }
//    }
//
//    override suspend fun observeSettings(): Flow<List<SettingEntity>> {
//        msf.emit(dao.getAllSettings())
//        return msf.asSharedFlow()
//    }
//
//    override suspend fun resetToDefaults() {
//        mutex.withLock {
//            dao.getAllSettings()
//            // todo impl?
//        }
//    }
//}