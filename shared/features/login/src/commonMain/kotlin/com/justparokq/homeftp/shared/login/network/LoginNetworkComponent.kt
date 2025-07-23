package com.justparokq.homeftp.shared.login.network

import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.login.LoginRequest
import com.justparokq.homeftp.shared.login.LoginResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class LoginNetworkComponent(
    private val httpClient: HttpClient,
) {

    /**
     * Sends a login request to the backend using the provided [loginRequest] and [baseUrl].
     *
     * Emits the following [Result] states in order:
     * - [Result.Loading(true)] before starting the network request
     * - [Result.Success] with [LoginResponse] if the login is successful (HTTP 200)
     * - [Result.Error] if the response status is not OK or if an exception occurs
     * - [Result.Loading(false)] after the network request completes (in all cases)
     *
     * @param loginRequest The login credentials to send to the backend.
     * @param baseUrl The base URL of the backend server (should not end with a slash).
     * @return A [Flow] emitting [Result] states representing the progress and outcome of the login request.
     *
     * @throws Exception if the network request fails unexpectedly. The error is caught and emitted as [Result.Error].
     */
    fun sendLoginRequest(
        loginRequest: LoginRequest,
        baseUrl: String,
    ): Flow<Result<LoginResponse>> {
        return flow {
            emit(Result.Loading(true))
            try {
                val result = httpClient.request("${baseUrl}/login") {
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    setBody(loginRequest)
                }
                emit(Result.Loading(false))
                if (result.status == HttpStatusCode.OK) {
                    val resultBody = result.body<LoginResponse>()
                    emit(Result.Success(resultBody))
                } else {
                    emit(Result.Error(errorMessage = "failed with error: ${result.status.value}"))
                }
            } catch (e: Exception) {
                emit(Result.Loading(false))
                emit(Result.Error(errorMessage = e.message ?: "Unknown error"))
            }
        }
    }
}