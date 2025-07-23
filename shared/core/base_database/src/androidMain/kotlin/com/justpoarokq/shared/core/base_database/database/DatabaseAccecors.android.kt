package com.justpoarokq.shared.core.base_database.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

internal actual fun getSettingsDatabaseBuilder(context: Any?): RoomDatabase.Builder<SettingDatabase> {
    val context = (context as? Context) ?: error("Couldn't provide context")
    val dbFile = context.getDatabasePath("setting.db")
    return Room.databaseBuilder<SettingDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
}