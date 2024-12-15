package com.justparokq.homeftp.shared.main.domain

import com.justparokq.homeftp.shared.root.ProjectFeature

class FeatureParamsModelMapper {

    fun map(featureToggle: FeatureToggle): FeatureParamsModel {
        return FeatureParamsModel(
            name = featureToggle.name,
            // todo fix mapper for different features :)
            feature = ProjectFeature.FTP,
            isEnabled = featureToggle.isEnabled,
            backgroundColor = 1,
        )
    }
}