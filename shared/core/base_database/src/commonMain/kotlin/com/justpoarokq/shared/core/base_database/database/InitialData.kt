package com.justpoarokq.shared.core.base_database.database

import androidx.sqlite.SQLiteConnection
import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justpoarokq.shared.core.base_database.entity.BooleanSettingEntity
import com.justpoarokq.shared.core.base_database.entity.SettingCategory
import com.justpoarokq.shared.core.base_database.entity.StringSettingEntity

internal fun doInitialSettingsSetup(connection: SQLiteConnection) {
    val settings = listOf(
        BooleanSettingEntity(
            name = Setting.FeatureKey.Ftp.key,
            description = "Connect with home server",
            category = SettingCategory.Feature,
            value = true
        ),
        BooleanSettingEntity(
            name = Setting.FeatureKey.Settings.key,
            description = "Represents all settings and features in app",
            category = SettingCategory.Feature,
            value = true
        ),
        StringSettingEntity(
            name = Setting.NetworkKey.Target.key,
            description = "Network target environment",
            category = SettingCategory.Network,
            value = Setting.NetworkKey.Target.Option.Mock.name
        )
    )

    // Use prepared statement with proper parameter binding
    settings.forEach { setting ->
        val (table, valueBinder) = when (setting) {
            is BooleanSettingEntity -> "BooleanSettingEntity" to { stmt: androidx.sqlite.SQLiteStatement, idx: Int -> stmt.bindLong(idx, if (setting.value) 1L else 0L) }
            is StringSettingEntity -> "StringSettingEntity" to { stmt: androidx.sqlite.SQLiteStatement, idx: Int -> stmt.bindText(idx, setting.value) }
            else -> error("Unknown setting type")
        }
        val sql = """
            INSERT OR REPLACE INTO $table (name, description, category, value) 
            VALUES (?, ?, ?, ?)
        """.trimIndent()
        connection.prepare(sql).use { statement ->
            statement.bindText(1, setting.name)
            statement.bindText(2, setting.description)
            statement.bindText(3, setting.category.name)
            valueBinder(statement, 4)
            statement.step()
        }
    }
}