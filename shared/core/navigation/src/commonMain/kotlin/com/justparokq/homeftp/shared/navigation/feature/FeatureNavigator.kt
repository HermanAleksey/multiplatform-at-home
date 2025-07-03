package com.justparokq.homeftp.shared.navigation.feature

interface FeatureNavigator {

    fun navigate(feature: ProjectFeature)

    fun replaceCurrentWith(feature: ProjectFeature)
}