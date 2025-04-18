package com.justpoarokq.shared.core.base_database.entity

internal enum class SettingCategory {

    Feature, Network;

    companion object {

        fun fromString(str: String?): SettingCategory? {
            return entries.find { it.name.equals(str, ignoreCase = true) }
        }
    }
}