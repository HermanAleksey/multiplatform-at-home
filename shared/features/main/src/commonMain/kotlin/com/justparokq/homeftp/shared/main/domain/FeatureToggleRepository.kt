package com.justparokq.homeftp.shared.main.domain

import kotlinx.coroutines.flow.Flow

internal interface FeatureToggleRepository {

    /**
     * Observes all feature toggles as a reactive stream.
     * @return Flow that emits the list of all feature toggles
     */
    suspend fun getAll(): Flow<List<FeatureToggle>>
}