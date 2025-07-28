package com.justparokq.homefpt.shared.core.network.httpclient

import com.justparokq.homefpt.shared.core.network.url.UrlResolver
import com.justparokq.homeftp.shared.core.setting_store.NetworkStore
import com.justparokq.homeftp.shared.login.RefreshRequest
import com.justparokq.homeftp.shared.login.RefreshResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headers

interface RefreshTokenRetrier {

    suspend fun tryToRefreshAccessToken(): Boolean
}

internal class RefreshTokenRetrierImpl(
    private val networkStore: NetworkStore,
    private val httpClient: HttpClient,
    private val urlResolver: UrlResolver,
) : RefreshTokenRetrier {

    // @return true if successfully gained new token, false otherwise
    override suspend fun tryToRefreshAccessToken(): Boolean {
        val refreshResponse = refreshAccessToken() ?: return false

        // Save new values
        networkStore.setRefreshToken(refreshResponse.refreshToken)
        networkStore.setAccessToken(refreshResponse.token)

        return true
    }

    private suspend fun refreshAccessToken(): RefreshResponse? {
        val refreshToken = networkStore.getRefreshToken() ?: return null

        return try {
            val response = httpClient.post("${urlResolver.getBaseUrl()}/refresh") {
                setBody(RefreshRequest(refreshToken = refreshToken))
                headers {
                    contentType(ContentType.Application.Json)
                }
            }
            if (response.status == HttpStatusCode.OK) {
                response.body<RefreshResponse>()
            } else {
                null
            }
        } catch (_: Exception) {
            null
        }
    }
}