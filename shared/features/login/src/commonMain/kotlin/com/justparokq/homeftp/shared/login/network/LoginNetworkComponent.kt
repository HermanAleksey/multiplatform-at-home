package com.justparokq.homeftp.shared.login.network

import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justparokq.homeftp.shared.login.LoginRequest
import com.justparokq.homeftp.shared.login.LoginResponse
import com.justparokq.homeftp.shared.utils.localhostUrl
import com.justpoarokq.shared.core.base_database.api.NetworkSettingsInteractor
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

interface LoginRepository {
    suspend fun sendLoginRequest(loginRequest: LoginRequest): Flow<Result<LoginResponse>>
}

internal class LoginRepositoryImpl(
    private val networkSettingsInteractor: NetworkSettingsInteractor,
    private val loginNetworkComponent: LoginNetworkComponent,
) : LoginRepository {

    override suspend fun sendLoginRequest(loginRequest: LoginRequest): Flow<Result<LoginResponse>> {
        val option = networkSettingsInteractor.getTargetOption()
        return when (option) {
            Setting.NetworkKey.Target.Option.Mock -> {
                flow {
                    emit(Result.Loading(true))
                    delay(500)
                    emit(Result.Success(LoginResponse("fake_token")))
                    emit(Result.Loading(false))
                }
            }

            Setting.NetworkKey.Target.Option.Dev -> {
                val baseUrl = "http://$localhostUrl:8080/dev"
                loginNetworkComponent.sendLoginRequest(loginRequest, baseUrl)
            }

            Setting.NetworkKey.Target.Option.Prod -> {
                val baseUrl = "http://$localhostUrl:8080"
                loginNetworkComponent.sendLoginRequest(loginRequest, baseUrl)
            }
        }
    }
}

internal class LoginNetworkComponent(
    private val httpClient: HttpClient,
) {

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