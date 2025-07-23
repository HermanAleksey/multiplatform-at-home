package com.justparokq.homeftp.shared.core.setting_key

/**
 * Type-safe representation of all feature keys in the project.
 */
sealed class Setting(val key: String) {

    sealed class FeatureKey(key: String) : Setting(key) {

        data object Ftp : FeatureKey("ftp")
        data object Settings : FeatureKey("settings")
        data object Name : FeatureKey("name")
    }

    sealed class NetworkKey(key: String) : Setting(key) {

        data object Target : FeatureKey("target") {

            enum class Option {
                Mock, Dev, Prod;

                companion object {

                    fun fromString(value: String): Option? =
                        entries.find { it.name.equals(value, ignoreCase = true) }
                }

                fun toStringValue(): String = name
            }
        }
    }

    companion object {

        fun fromKey(key: String): Setting? {
            return when (key) {
                FeatureKey.Ftp.key -> FeatureKey.Ftp
                FeatureKey.Settings.key -> FeatureKey.Settings
                FeatureKey.Name.key -> FeatureKey.Name
                NetworkKey.Target.key -> NetworkKey.Target
                else -> null
            }
        }
    }
}
