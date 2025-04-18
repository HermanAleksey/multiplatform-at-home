package com.justparokq.homeftp.shared.main.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature

internal class FeatureParamsModelMapper {

    fun map(featureToggle: FeatureToggle): FeatureParamsModel {
        return when (featureToggle.name) {
            "ftp" -> FeatureParamsModel(
                name = featureToggle.name,
                // todo fix mapper for different features :)
                feature = ProjectFeature.FTP,
                isEnabled = featureToggle.isEnabled,
                backgroundColor = 1,
            )

            "settings" -> FeatureParamsModel(
                name = featureToggle.name,
                // todo fix mapper for different features :)
                feature = ProjectFeature.SETTINGS,
                isEnabled = featureToggle.isEnabled,
                backgroundColor = Color.Green.toArgb(),
            )

            else -> error("Do normal feature toggles pls")
        }
    }
}