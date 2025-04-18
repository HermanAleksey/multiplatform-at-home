package com.justpoarokq.shared.core.base_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class BooleanSettingEntity(
    @PrimaryKey
    val name: String,
    val description: String,
    val category: SettingCategory,
    val value: Boolean,
)