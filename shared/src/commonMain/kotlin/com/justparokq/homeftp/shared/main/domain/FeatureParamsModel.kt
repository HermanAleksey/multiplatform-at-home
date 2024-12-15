package com.justparokq.homeftp.shared.main.domain

import com.justparokq.homeftp.shared.root.ProjectFeature

data class FeatureParamsModel(
    val name: String,
    val feature: ProjectFeature,
    val isEnabled: Boolean,
    val backgroundColor: Int
)