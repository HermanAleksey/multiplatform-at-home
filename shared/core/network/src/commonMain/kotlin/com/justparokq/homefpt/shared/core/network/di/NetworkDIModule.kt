package com.justparokq.homefpt.shared.core.network.di

import com.justparokq.homefpt.shared.core.network.UrlResolver
import com.justparokq.homefpt.shared.core.network.httpclient.AuthHttpClient
import com.justparokq.homefpt.shared.core.network.httpclient.AuthHttpClientImpl
import com.justparokq.homefpt.shared.core.network.httpclient.getUnauthHttpClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkCoreModule = module {
    single<HttpClient>(createdAtStart = true) { getUnauthHttpClient() }
    single<AuthHttpClient>(createdAtStart = true) {
        AuthHttpClientImpl(
            networkStore = get(),
            httpClient = get(),
            urlResolver = get()
        )
    }
    factory<UrlResolver> { UrlResolver(networkSettingsInteractor = get()) }
}
