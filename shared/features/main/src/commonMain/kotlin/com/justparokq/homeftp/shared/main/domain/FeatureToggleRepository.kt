package com.justparokq.homeftp.shared.main.domain

import kotlinx.coroutines.flow.Flow

internal interface FeatureToggleRepository {

    /**
     * Observes all feature toggles as a reactive stream.
     * @return Flow that emits the list of all feature toggles
     */
    suspend fun getAll(): Flow<List<FeatureToggle>>

    /**
     * Checks if a specific feature is enabled.
     * @param featureName The name of the feature to check
     * @return true if the feature is enabled, false otherwise
     */
    suspend fun isFeatureEnabled(featureName: String): Boolean
}