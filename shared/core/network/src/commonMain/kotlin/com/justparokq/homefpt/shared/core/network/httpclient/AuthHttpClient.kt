package com.justparokq.homefpt.shared.core.network.httpclient

import com.justparokq.homefpt.shared.core.network.Const.AUTH_HEADER
import com.justparokq.homefpt.shared.core.network.expection.NotAuthorizedException
import com.justparokq.homefpt.shared.core.network.navigation.UnauthNavigator
import com.justparokq.homefpt.shared.core.network.url.UrlResolver
import com.justparokq.homeftp.shared.core.setting_store.NetworkStore
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers

interface AuthHttpClient {

    suspend fun authorizedRequest(
        endpoint: String,
        block: HttpRequestBuilder.() -> Unit = {},
    ): HttpResponse

    suspend fun authorizedRequest(
        baseUrl: String,
        endpoint: String,
        block: HttpRequestBuilder.() -> Unit = {},
    ): HttpResponse
}

internal class AuthHttpClientImpl(
    private val networkStore: NetworkStore,
    val httpClient: HttpClient,
    val urlResolver: UrlResolver,
    val refreshTokenRetrier: RefreshTokenRetrier,
    val unauthNavigator: UnauthNavigator,
) : AuthHttpClient {

    override suspend fun authorizedRequest(
        endpoint: String,
        block: HttpRequestBuilder.() -> Unit,
    ): HttpResponse {
        val baseUrl = urlResolver.getBaseUrl()
        return authorizedRequest(
            baseUrl = baseUrl,
            endpoint = endpoint,
            block = block
        )
    }

    override suspend fun authorizedRequest(
        baseUrl: String,
        endpoint: String,
        block: HttpRequestBuilder.() -> Unit,
    ): HttpResponse {
        val accessToken = networkStore.getAccessToken() ?: throw NotAuthorizedException()

        suspend fun makeRequestWithAuthHeader(): HttpResponse {
            return httpClient.request(urlString = "$baseUrl$endpoint") {
                // headers first because new values does not override existing
                headers {
                    header(AUTH_HEADER, "Bearer $accessToken")
                }
                block()
            }
        }

        val result = makeRequestWithAuthHeader()
        return if (result.status == HttpStatusCode.Unauthorized) {
            refreshTokenAndRetry(retry = { makeRequestWithAuthHeader() }) ?: result
        } else return result
    }

    private suspend fun refreshTokenAndRetry(retry: suspend () -> HttpResponse): HttpResponse? {
        val isTokenRefreshed = refreshTokenRetrier.tryToRefreshAccessToken()
        if (isTokenRefreshed) {
            // retry request 1 more time
            val newResult = retry()
            if (newResult.status == HttpStatusCode.Unauthorized) {
                unauthNavigator.navigateToUnauthZone()
                return newResult
            } else {
                return newResult
            }
        } else {
            unauthNavigator.navigateToUnauthZone()
            return null
        }
    }
}