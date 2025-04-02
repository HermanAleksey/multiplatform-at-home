package com.justparokq.homeftp.shared.main.domain

interface FeatureToggleRepository {

    fun getAll(): List<FeatureToggle>
}