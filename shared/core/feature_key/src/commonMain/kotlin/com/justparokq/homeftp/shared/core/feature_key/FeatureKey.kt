package com.justparokq.homeftp.shared.core.feature_key

/**
 * Type-safe representation of all feature keys in the project.
 */
sealed class FeatureKey(val key: String) {
    object Ftp : FeatureKey("ftp")
    object Settings : FeatureKey("settings")

    companion object {
        fun fromKey(key: String): FeatureKey? = when (key) {
            Ftp.key -> Ftp
            Settings.key -> Settings
            else -> null
        }
    }
}
