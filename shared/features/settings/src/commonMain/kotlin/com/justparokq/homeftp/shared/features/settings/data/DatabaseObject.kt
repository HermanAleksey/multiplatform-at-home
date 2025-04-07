package com.justparokq.homeftp.shared.features.settings.data

import com.justpoarokq.shared.core.base_database.database.SettingDatabase
import com.justpoarokq.shared.core.base_database.database.getSettingsDatabaseBuilder
import com.justpoarokq.shared.core.base_database.database.getSettingsDatabase

// im am DI...
object DatabaseObject {

    private var db: SettingDatabase? = null

    fun init(context: Any?) {
        if (db == null) db = getSettingsDatabase(getSettingsDatabaseBuilder(context))
    }

    fun settings(): SettingDatabase = db ?: error("Init first")
}