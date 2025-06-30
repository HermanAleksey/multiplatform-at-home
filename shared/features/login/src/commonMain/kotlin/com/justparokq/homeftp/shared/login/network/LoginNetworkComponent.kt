package com.justparokq.homeftp.shared.login.network

import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.login.LoginRequest
import com.justparokq.homeftp.shared.login.LoginResponse
import com.justparokq.homeftp.shared.utils.localhostUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class LoginNetworkComponent(
    private val httpClient: HttpClient,
) {

    fun sendLoginRequest(
        loginRequest: LoginRequest,
    ): Flow<Result<LoginResponse>> {
        return flow {
            emit(Result.Loading(true))
            // todo remove; delay for real server emulation purposes
            delay(1000)
            if (true /*isDebug*/) {
                emit(Result.Success(LoginResponse("tokeee")))
                emit(Result.Loading(false))
                return@flow
            }
            try {
                val result = httpClient.request("http://$localhostUrl:8080/login") {
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