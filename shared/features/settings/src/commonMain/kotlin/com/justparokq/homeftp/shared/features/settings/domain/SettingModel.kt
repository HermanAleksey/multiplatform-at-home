package com.justparokq.homeftp.shared.features.settings.domain

data class SettingModel(
    val name: String,
    val description: String,
    val category: Category,
    val value: Value
) {

    enum class Category {

        Network, Features;
    }

    sealed interface Value {

        data class Boolean(
            val value: kotlin.Boolean
        ) : Value
    }
}