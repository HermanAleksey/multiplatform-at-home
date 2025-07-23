package com.justpoarokq.shared.core.base_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.justpoarokq.shared.core.base_database.entity.StringSettingEntity

@Dao
internal interface StringSettingDao {

    @Upsert
    suspend fun insert(setting: StringSettingEntity): Long

    @Query("SELECT * FROM StringSettingEntity WHERE name = :name")
    suspend fun getSettingByName(name: String): StringSettingEntity?

    @Delete
    suspend fun delete(setting: StringSettingEntity)

    @Query("DELETE FROM StringSettingEntity WHERE name = :name")
    suspend fun deleteByName(name: String): Int

    @Query("SELECT * FROM StringSettingEntity")
    suspend fun getAllSettings(): List<StringSettingEntity>

    @Query("DELETE FROM StringSettingEntity")
    suspend fun clearAll()
}