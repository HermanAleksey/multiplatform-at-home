package com.justpoarokq.shared.core.base_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

internal open class SettingEntity(
    open val name: String,
    open val description: String,
    open val category: SettingCategory
)

@Entity
internal data class BooleanSettingEntity(
    @PrimaryKey
    override val name: String,
    override val description: String,
    override val category: SettingCategory,
    val value: Boolean,
) : SettingEntity(name, description, category)

@Entity
internal data class StringSettingEntity(
    @PrimaryKey
    override val name: String,
    override val description: String,
    override val category: SettingCategory,
    val value: String,
) : SettingEntity(name, description, category)

internal enum class SettingCategory {

    Feature, Network;

    companion object {

        fun fromString(str: String?): SettingCategory? {
            return entries.find { it.name.equals(str, ignoreCase = true) }
        }
    }
}