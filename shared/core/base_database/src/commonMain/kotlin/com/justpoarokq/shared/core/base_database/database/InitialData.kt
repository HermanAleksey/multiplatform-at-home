package com.justpoarokq.shared.core.base_database.database

import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.justpoarokq.shared.core.base_database.entity.BooleanSettingEntity
import com.justpoarokq.shared.core.base_database.entity.SettingCategory

internal fun doInitialSettingsSetup(connection: SQLiteConnection) {
    val settings = listOf(
        BooleanSettingEntity(
            name = "Easy log in",
            description = "Allows to log in without login and password",
            category = SettingCategory.Feature,
            value = true
        )
    )
    val settingsInitialDataAsString = settings.map {
        "('${it.name}', '${it.description}', '${it.category}', '${it.value}')"
    }.foldIndexed("") { index, acc, newStr ->
        val endOfLineStr = if (index == settings.lastIndex) ";" else ",\n"
        acc + newStr + endOfLineStr
    }

    connection.execSQL(
        """
        INSERT INTO BooleanSettingEntity (name, description, category, value) 
        VALUES $settingsInitialDataAsString
    """.trimIndent()
    )
}