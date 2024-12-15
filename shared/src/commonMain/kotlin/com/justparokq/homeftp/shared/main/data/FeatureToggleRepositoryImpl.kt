package com.justparokq.homeftp.shared.main.data

import com.justparokq.homeftp.shared.main.domain.FeatureToggle
import com.justparokq.homeftp.shared.main.domain.FeatureToggleRepository

class FeatureToggleRepositoryImpl : FeatureToggleRepository {

    override fun getAll(): List<FeatureToggle> {
        // todo add database connection
        return listOf(
            FeatureToggle(
                name = "ftp",
                isEnabled = true
            )
        )
    }

    override fun set(featureToggle: FeatureToggle) {
        TODO("Not yet implemented")
    }
}