package com.justparokq.homeftp.shared.root.presentation

import com.justparokq.homeftp.shared.features.settings.api.settingsModule
import com.justparokq.homeftp.shared.ftp.api.ftpModule
import com.justparokq.homeftp.shared.login.api.loginModule
import com.justparokq.homeftp.shared.main.api.mainModule
import com.justpoarokq.shared.core.base_database.api.baseDatabaseModule
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

//fun startKoinApp(contextFactory: ContextFactory): KoinApplication {
////    val utilityModule = module {
////        single<ContextFactory> { contextFactory }
////        factory<Any?>(named("Context")) { contextFactory.getContext() }
////        factory<Any?>(named("Application")) { contextFactory.getApplication() }
////    }
//    return startKoin {
//        val logger = object : Logger() {
//            override fun log(level: Level, msg: MESSAGE) {
//                println("Koin log: $msg")
//            }
//        }
//        logger(LEVEL.INFO)
//        // core modules
//        modules( baseDatabaseModule)
//        // feature modules
//        modules(loginModule, mainModule, ftpModule, settingsModule)
//    }
//}

val startKoin by lazy {
    startKoin {
        val logger = object : Logger() {
            override fun log(level: Level, msg: MESSAGE) {
                println("Koin log: $msg")
            }
        }
        logger(logger)
        // core modules
        modules(baseDatabaseModule)
        // feature modules
        modules(loginModule, mainModule, ftpModule, settingsModule)
    }
}