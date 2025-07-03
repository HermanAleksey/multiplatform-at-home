package com.justparokq.homeftp.shared.main.domain

import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature

internal data class FeatureParamsModel(
    val key: Setting,
    val feature: ProjectFeature,
    val isEnabled: Boolean,
    val imageUrl: String? = null
)