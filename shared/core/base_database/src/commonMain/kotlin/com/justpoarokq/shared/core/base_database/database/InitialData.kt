package com.justpoarokq.shared.core.base_database.database

import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.justpoarokq.shared.core.base_database.entity.SettingEntity

internal fun doInitialSettingsSetup(connection: SQLiteConnection) {
    val settings = listOf(
        SettingEntity(
            id = 0,
            name = "Easy log in",
            description = "Allows to log in without login and password",
            category = "features",
            value = "true"
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
        INSERT INTO SettingEntity (name, description, category, value) 
        VALUES $settingsInitialDataAsString
    """.trimIndent()
    )
}