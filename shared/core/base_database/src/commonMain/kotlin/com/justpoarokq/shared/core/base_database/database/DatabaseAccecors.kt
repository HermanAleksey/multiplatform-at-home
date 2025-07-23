package com.justpoarokq.shared.core.base_database.database

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

internal expect fun getSettingsDatabaseBuilder(context: Any?): RoomDatabase.Builder<SettingDatabase>

internal fun getSettingsDatabase(
    builder: RoomDatabase.Builder<SettingDatabase>
): SettingDatabase {
    return builder
//        .addMigrations(INITIAL_MIGRATION)
        .fallbackToDestructiveMigrationOnDowngrade(true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(connection: SQLiteConnection) {
                super.onCreate(connection)
                doInitialSetup(connection)
            }
        })
        .build()
}

private fun doInitialSetup(connection: SQLiteConnection) {
    doInitialSettingsSetup(connection)
}