package com.justparokq.homeftp.shared.root.presentation.navigation

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.push
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature

internal class FeatureNavigatorImpl(
    private val stackNavigation: StackNavigation<Config>
) : FeatureNavigator {

    override fun navigate(feature: ProjectFeature) {
        when (feature) {
            ProjectFeature.LOGIN -> stackNavigation.push(Config.Login)
            ProjectFeature.MAIN -> stackNavigation.push(Config.Main)
            ProjectFeature.FTP -> stackNavigation.push(Config.Ftp)
            ProjectFeature.SETTINGS -> stackNavigation.push(Config.Settings)
        }
    }
}