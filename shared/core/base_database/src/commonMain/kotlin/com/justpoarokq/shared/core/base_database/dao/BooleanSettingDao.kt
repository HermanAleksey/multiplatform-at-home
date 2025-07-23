package com.justpoarokq.shared.core.base_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.justpoarokq.shared.core.base_database.entity.BooleanSettingEntity

@Dao
internal interface BooleanSettingDao {

    @Upsert
    suspend fun insert(setting: BooleanSettingEntity): Long

    @Query("SELECT * FROM BooleanSettingEntity WHERE name = :name")
    suspend fun getSettingByName(name: String): BooleanSettingEntity?

    @Delete
    suspend fun delete(setting: BooleanSettingEntity)

    @Query("DELETE FROM BooleanSettingEntity WHERE name = :name")
    suspend fun deleteByName(name: String): Int

    @Query("SELECT * FROM BooleanSettingEntity")
    suspend fun getAllSettings(): List<BooleanSettingEntity>

    @Query("DELETE FROM BooleanSettingEntity")
    suspend fun clearAll()
}