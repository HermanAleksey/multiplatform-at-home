package com.justpoarokq.shared.core.base_database.database

import androidx.sqlite.SQLiteConnection
import com.justpoarokq.shared.core.base_database.entity.BooleanSettingEntity
import com.justpoarokq.shared.core.base_database.entity.SettingCategory
import com.justparokq.homeftp.shared.core.feature_key.FeatureKey

internal fun doInitialSettingsSetup(connection: SQLiteConnection) {
    val settings = listOf(
        BooleanSettingEntity(
            name = FeatureKey.Ftp.key,
            description = "Connect with home server",
            category = SettingCategory.Feature,
            value = true
        ),
        BooleanSettingEntity(
            name = FeatureKey.Settings.key,
            description = "Represents all settings and features in app",
            category = SettingCategory.Feature,
            value = true
        ),
    )

    // Use prepared statement with proper parameter binding
    settings.forEach { setting ->
        val sql = """
            INSERT OR REPLACE INTO BooleanSettingEntity (name, description, category, value) 
            VALUES (?, ?, ?, ?)
        """.trimIndent()

        connection.prepare(sql).use { statement ->
            statement.bindText(1, setting.name)
            statement.bindText(2, setting.description)
            statement.bindText(3, setting.category.name)
            statement.bindLong(4, if (setting.value) 1L else 0L)
            statement.step()
        }
    }
}