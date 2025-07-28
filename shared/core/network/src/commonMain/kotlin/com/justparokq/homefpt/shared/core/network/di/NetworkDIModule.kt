package com.justparokq.homefpt.shared.core.network.di

import com.justparokq.homefpt.shared.core.network.coil.RequestHeaderInterceptor
import com.justparokq.homefpt.shared.core.network.httpclient.AuthHttpClient
import com.justparokq.homefpt.shared.core.network.httpclient.AuthHttpClientImpl
import com.justparokq.homefpt.shared.core.network.httpclient.RefreshTokenRetrier
import com.justparokq.homefpt.shared.core.network.httpclient.RefreshTokenRetrierImpl
import com.justparokq.homefpt.shared.core.network.httpclient.getUnauthHttpClient
import com.justparokq.homefpt.shared.core.network.url.UrlResolver
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkCoreModule = module {
    single<RequestHeaderInterceptor>(createdAtStart = false) {
        RequestHeaderInterceptor(networkStore = get())
    }
    factory<RefreshTokenRetrier> {
        RefreshTokenRetrierImpl(
            networkStore = get(),
            httpClient = get(),
            urlResolver = get()
        )
    }
    single<HttpClient>(createdAtStart = true) { getUnauthHttpClient() }
    single<AuthHttpClient>(createdAtStart = false) {
        AuthHttpClientImpl(
            networkStore = get(),
            httpClient = get(),
            urlResolver = get(),
            refreshTokenRetrier = get(),
            unauthNavigator = get()
        )
    }
    factory<UrlResolver> { UrlResolver(networkSettingsInteractor = get()) }
}
