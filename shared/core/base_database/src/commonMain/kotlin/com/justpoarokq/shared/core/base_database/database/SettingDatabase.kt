package com.justpoarokq.shared.core.base_database.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.justpoarokq.shared.core.base_database.dao.SettingDao
import com.justpoarokq.shared.core.base_database.entity.SettingEntity

@Database(entities = [SettingEntity::class], version = 1)
// @TypeConverters(UserConverter::class) -> Include for complex data class
@ConstructedBy(AppDatabaseConstructor::class)
abstract class SettingDatabase : RoomDatabase() {

    abstract fun settingDao(): SettingDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<SettingDatabase> {
    override fun initialize(): SettingDatabase
}