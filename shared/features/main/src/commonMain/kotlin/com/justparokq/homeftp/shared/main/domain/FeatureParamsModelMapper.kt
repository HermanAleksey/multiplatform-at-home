package com.justparokq.homeftp.shared.main.domain

import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature

internal class FeatureParamsModelMapper {

    private val featureToProjectFeature = mapOf(
        Setting.FeatureKey.Ftp to ProjectFeature.FTP,
        Setting.FeatureKey.Settings to ProjectFeature.SETTINGS
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