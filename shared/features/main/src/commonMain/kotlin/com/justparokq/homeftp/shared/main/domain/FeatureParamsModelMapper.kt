package com.justparokq.homeftp.shared.main.domain

import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature
import com.justparokq.homeftp.shared.core.feature_key.FeatureKey

internal class FeatureParamsModelMapper {

    private val featureToProjectFeature = mapOf(
        FeatureKey.Ftp to ProjectFeature.FTP,
        FeatureKey.Settings to ProjectFeature.SETTINGS
        // Add more features here as needed
    )

    fun map(featureToggle: FeatureToggle): FeatureParamsModel {
        val projectFeature = featureToProjectFeature[featureToggle.key]
            ?: error("Unknown feature key: '${featureToggle.key}'. Please add it to FeatureParamsModelMapper.")
        return FeatureParamsModel(
            key = featureToggle.key,
            feature = projectFeature,
            isEnabled = featureToggle.isEnabled,
            imageUrl = null
        )
    }
}