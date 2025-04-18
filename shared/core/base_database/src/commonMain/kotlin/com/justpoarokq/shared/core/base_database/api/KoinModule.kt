package com.justpoarokq.shared.core.base_database.api

import androidx.room.RoomDatabase
import com.justpoarokq.shared.core.base_database.database.SettingDatabase
import com.justpoarokq.shared.core.base_database.database.getSettingsDatabase
import org.koin.dsl.module

val baseDatabaseModule = module {
    // Factory (creates new instance each time)
    single<SettingDatabase> { (builder: RoomDatabase.Builder<SettingDatabase>) ->
        getSettingsDatabase(
            builder
        )
    }
}