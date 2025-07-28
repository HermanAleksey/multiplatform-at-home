package com.justparokq.homeftp.shared.root.presentation.navigation

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.navigation.feature.ProjectFeature

internal class FeatureNavigatorImpl(
    private val stackNavigation: StackNavigation<Config>
) : FeatureNavigator {

    override fun navigate(feature: ProjectFeature) {
        val config = getConfigByFeature(feature)
        stackNavigation.push(config)
    }

    override fun replaceCurrentWith(feature: ProjectFeature) {
        val config = getConfigByFeature(feature)
        stackNavigation.replaceCurrent(config)
    }

    override fun popBackStack(): Boolean {
        var result = false
        stackNavigation.pop { isSuccess ->
            result = isSuccess
        }
        return result
    }

    override fun navigateToTheRoot() {
        stackNavigation.popTo(0)
    }

    private fun getConfigByFeature(feature: ProjectFeature): Config {
        return featureToConfig[feature] ?: error("Feature $feature not implemented in navigator")
    }

    private val featureToConfig: Map<ProjectFeature, Config> = hashMapOf(
        ProjectFeature.LOGIN to Config.Login,
        ProjectFeature.MAIN to Config.Main,
        ProjectFeature.FTP to Config.Ftp,
        ProjectFeature.SETTINGS to Config.Settings,
    )
}