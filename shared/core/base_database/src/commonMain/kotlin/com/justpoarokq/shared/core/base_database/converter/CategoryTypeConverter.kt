package com.justpoarokq.shared.core.base_database.converter

import androidx.room.TypeConverter
import com.justpoarokq.shared.core.base_database.entity.SettingCategory

internal class CategoryTypeConverter {

    @TypeConverter
    fun fromString(value: String?): SettingCategory? {
        return SettingCategory.fromString(value)
    }

    @TypeConverter
    fun toString(category: SettingCategory?): String {
        return category?.name ?: error("invalid category")
    }
}