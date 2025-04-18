package com.justparokq.homeftp.shared.main.domain

internal interface FeatureToggleRepository {

    fun getAll(): List<FeatureToggle>
}