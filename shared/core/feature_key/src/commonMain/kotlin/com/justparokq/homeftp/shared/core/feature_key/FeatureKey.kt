package com.justparokq.homeftp.shared.core.feature_key

/**
 * Type-safe representation of all feature keys in the project.
 */
sealed class FeatureKey(val key: String) {
    data object Ftp : FeatureKey("ftp")
    data object Settings : FeatureKey("settings")
}
