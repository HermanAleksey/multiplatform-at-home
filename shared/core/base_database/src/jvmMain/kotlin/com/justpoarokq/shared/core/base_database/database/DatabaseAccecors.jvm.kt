package com.justpoarokq.shared.core.base_database.database

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

internal actual fun getSettingsDatabaseBuilder(context: Any?): RoomDatabase.Builder<SettingDatabase> {
    // todo how to reinstall app? Had to use setting_new after changing
    val dbFile = File(System.getProperty("java.io.tmpdir"), "setting_new.db")
    return Room.databaseBuilder<SettingDatabase>(
        name = dbFile.absolutePath,
    )
}