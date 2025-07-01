package com.justparokq.homeftp.shared.main.domain

import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature
import com.justparokq.homeftp.shared.core.feature_key.FeatureKey

internal data class FeatureParamsModel(
    val key: FeatureKey,
    val feature: ProjectFeature,
    val isEnabled: Boolean,
    val imageUrl: String? = null
)