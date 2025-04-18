package com.justparokq.homeftp.shared.root.presentation

import com.justparokq.homeftp.shared.login.api.loginModule
import com.justparokq.homeftp.shared.main.api.mainModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

val koinApp by lazy {
    startKoin {
        val logger = object : Logger() {
            override fun log(level: Level, msg: MESSAGE) {
                println("Koin log: $msg")
            }
        }
        logger(logger)
        // core modules
        modules()
        // feature modules
        modules(loginModule, mainModule)
    }
}
