package com.justpoarokq.shared.core.base_database.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual fun getSettingsDatabaseBuilder(context: Any?): RoomDatabase.Builder<SettingDatabase> {
    val dbFile = File(System.getProperty("java.io.tmpdir"), "setting.db")
    return Room.databaseBuilder<SettingDatabase>(
        name = dbFile.absolutePath,
    )
}