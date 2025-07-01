package com.justparokq.homeftp.shared.main.domain

import com.justparokq.homeftp.shared.core.feature_key.FeatureKey

// todo implement in separate module
internal data class FeatureToggle (
    val key: FeatureKey,
    val isEnabled: Boolean
)