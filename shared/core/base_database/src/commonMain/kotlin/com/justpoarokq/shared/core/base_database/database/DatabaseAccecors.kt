package com.justpoarokq.shared.core.base_database.database

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

expect fun getSettingsDatabaseBuilder(context: Any?): RoomDatabase.Builder<SettingDatabase>

fun getySettingsDatabase(
    builder: RoomDatabase.Builder<SettingDatabase>
): SettingDatabase {
    return builder
        .addMigrations(INITIAL_MIGRATION)
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}