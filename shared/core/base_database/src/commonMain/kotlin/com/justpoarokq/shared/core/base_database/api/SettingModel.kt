package com.justpoarokq.shared.core.base_database.api

data class SettingModel(
    val name: String,
    val description: String,
    val category: Category,
    val value: Value
) {

    enum class Category {

        Network, Feature;
    }

    sealed interface Value {

        data class Boolean(
            val value: kotlin.Boolean
        ) : Value {

            fun isAvailable() = value
        }

        data class String(
            val value: kotlin.String
        ) : Value
    }
}