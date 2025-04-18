package com.justparokq.homeftp.shared.root.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Config {

    @Serializable
    data object Login : Config

    @Serializable
    data object Main : Config

    @Serializable
    data object Ftp : Config

    @Serializable
    data object Settings : Config
}