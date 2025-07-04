package com.justparokq.homeftp.shared.root.presentation

import com.justparokq.homeftp.shared.features.settings.api.settingsModule
import com.justparokq.homeftp.shared.ftp.api.ftpModule
import com.justparokq.homeftp.shared.login.api.loginModule
import com.justparokq.homeftp.shared.main.api.mainModule
import com.justparokq.homeftp.shared.utils.ContextFactory
import com.justpoarokq.shared.core.base_database.api.baseDatabaseModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import org.koin.core.qualifier.named
import org.koin.dsl.module

public fun startKoinImpl(contextFactory: ContextFactory) {
    startKoin {
        val logger = object : Logger() {
            override fun log(level: Level, msg: MESSAGE) {
                println("Koin log: $msg")
            }
        }
        logger(logger)

        // utility
        val utilityModule = module {
            single<ContextFactory> { contextFactory }
            factory<Any?>(named("Context")) { contextFactory.getContext() }
            factory<Any?>(named("Application")) { contextFactory.getApplication() }
        }
        loadKoinModules(utilityModule)

        // core modules
        modules(baseDatabaseModule)
        // feature modules
        modules(loginModule, mainModule, ftpModule, settingsModule)
    }
}