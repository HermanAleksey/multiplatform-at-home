package com.justpoarokq.shared.core.base_database.database

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val INITIAL_MIGRATION = object : Migration(0, 1) {

    override fun migrate(connection: SQLiteConnection) {
        super.migrate(connection)
        connection.execSQL(
            """
        INSERT INTO SettingEntity (name, description, category, value) 
        VALUES 
            ('Easy log in', 'Allows to log in without login and password', 'features', 'true'),
            ('SSL pinning', 'SSL pinning', 'network', 'true');
    """.trimIndent()
        )
    }
}