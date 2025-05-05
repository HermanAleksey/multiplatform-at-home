package com.justparokq.homeftp.core.network

import io.ktor.client.HttpClient
import org.koin.dsl.module

public val networkCoreModule = module {
    single<HttpClient>(createdAtStart = true) { getDefaultHttpClient() }
}