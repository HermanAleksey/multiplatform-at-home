package com.justpoarokq.shared.core.base_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.justpoarokq.shared.core.base_database.entity.SettingEntity

@Dao
interface SettingDao {

    @Upsert
    suspend fun insert(setting: SettingEntity): Long

    @Query("SELECT * FROM SettingEntity WHERE id = :id")
    suspend fun getSettingById(id: Int): SettingEntity?

    @Query("SELECT * FROM SettingEntity WHERE name = :name")
    suspend fun getSettingByName(name: String): SettingEntity?

    @Delete
    suspend fun delete(setting: SettingEntity)

    @Query("DELETE FROM SettingEntity WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Query("SELECT * FROM SettingEntity")
    suspend fun getAllSettings(): List<SettingEntity>
}