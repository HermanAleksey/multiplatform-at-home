package com.justpoarokq.shared.core.base_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// todo replace strings with enum :)
@Entity
data class SettingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val category: String,
    val value: String,
)