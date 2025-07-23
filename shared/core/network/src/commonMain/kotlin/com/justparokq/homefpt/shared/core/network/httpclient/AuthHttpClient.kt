package com.justparokq.homefpt.shared.core.network.httpclient

import com.justparokq.homefpt.shared.core.network.UrlResolver
import com.justparokq.homefpt.shared.core.network.expection.NotAuthorizedException
import com.justparokq.homeftp.shared.core.setting_store.NetworkStore
import com.justparokq.homeftp.shared.login.RefreshRequest
import com.justparokq.homeftp.shared.login.RefreshResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMessageBuilder
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headers

private const val AUTH_HEADER = "Authorization"

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
    // todo add logic if Login fail and Refresh fail - navigate to login screen
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

        val result = httpClient.request(urlString = "$baseUrl$endpoint") {
            // headers first because new values does not override existing
            headers {
                addAuthHeader(accessToken)
            }
            block()
        }
        if (result.status == HttpStatusCode.Unauthorized) {
            val refreshResponse = refreshAccessToken() ?: return result

            // Save new values
            networkStore.setRefreshToken(refreshResponse.refreshToken)
            networkStore.setAccessToken(refreshResponse.token)

            // Retry the original request with the new access token
            return httpClient.request {
                url(endpoint)
                block()
                headers {
                    addAuthHeader(refreshResponse.token)
                }
            }

        } else return result
    }

    private fun HttpMessageBuilder.addAuthHeader(token: String) {
        this.header(AUTH_HEADER, "Bearer $token")
    }

    suspend fun refreshAccessToken(): RefreshResponse? {
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