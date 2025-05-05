package com.justpoarokq.shared.core.base_database.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.justpoarokq.shared.core.base_database.converter.CategoryTypeConverter
import com.justpoarokq.shared.core.base_database.dao.BooleanSettingDao
import com.justpoarokq.shared.core.base_database.dao.StringSettingDao
import com.justpoarokq.shared.core.base_database.entity.BooleanSettingEntity
import com.justpoarokq.shared.core.base_database.entity.StringSettingEntity

@Database(entities = [BooleanSettingEntity::class, StringSettingEntity::class], version = 1)
@TypeConverters(CategoryTypeConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
internal abstract class SettingDatabase : RoomDatabase() {

    abstract fun booleanSettingDao(): BooleanSettingDao
    abstract fun stringSettingDao(): StringSettingDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect object AppDatabaseConstructor : RoomDatabaseConstructor<SettingDatabase> {
    override fun initialize(): SettingDatabase
}