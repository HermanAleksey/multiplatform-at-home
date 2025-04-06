package com.justparokq.homeftp.shared.features.settings.domain

data class SettingModel(
    val name: String,
    val description: String,
    val category: Category,
    val value: Value
) {

    enum class Category {

        Network, Features;

        companion object {
            fun fromString(str: String): Category? {
                return entries.find { it.name == str }
            }
        }
    }

    sealed interface Value {

        data class Boolean(
            val value: kotlin.Boolean
        ) : Value

        companion object {

            fun fromString(value: Any?): Value? {

                return when (value) {
                    is kotlin.Boolean -> Value.Boolean(value = value)
                    is String -> {
                        Value.Boolean(value.toBoolean())
                    }

                    else -> null
                }
            }
        }
    }
}