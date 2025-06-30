package com.justparokq.homeftp.shared.main.domain

import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature

internal class FeatureParamsModelMapper {

    private val featureDefinitions = mapOf(
        "ftp" to FeatureDefinition(ProjectFeature.FTP, null),
        "settings" to FeatureDefinition(ProjectFeature.SETTINGS, null)
        // Add more features here as needed
    )

    fun map(featureToggle: FeatureToggle): FeatureParamsModel {
        val def = featureDefinitions[featureToggle.name]
            ?: error("Unknown feature name: '${featureToggle.name}'. Please add it to FeatureParamsModelMapper.")
        return FeatureParamsModel(
            name = featureToggle.name,
            feature = def.feature,
            isEnabled = featureToggle.isEnabled,
            imageUrl = def.imageUrl
        )
    }

    private data class FeatureDefinition(
        val feature: ProjectFeature,
        val imageUrl: String?
    )
}