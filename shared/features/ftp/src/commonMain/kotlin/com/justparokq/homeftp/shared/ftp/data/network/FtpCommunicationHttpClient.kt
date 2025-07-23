package com.justparokq.homeftp.shared.ftp.data.network

import com.justparokq.homefpt.shared.core.network.httpclient.AuthHttpClient
import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.ftp.FileResponse
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal interface FtpCommunicationHttpClient {

    fun getDirectoryContent(directoryUri: String): Flow<Result<List<FileResponse>>>
}

internal class FtpCommunicationHttpClientImpl(
    private val httpClient: AuthHttpClient
) : FtpCommunicationHttpClient {

    override fun getDirectoryContent(
        directoryUri: String,
    ): Flow<Result<List<FileResponse>>> {
        return flow {
            emit(Result.Loading(true))
            try {
                val result = httpClient.authorizedRequest(endpoint = "/directory") {
                    if (directoryUri.isNotEmpty()) {
                        parameter("path", directoryUri)
                    }
                    method = HttpMethod.Get
                    contentType(ContentType.Application.Json)
                }

                emit(Result.Loading(false))

                if (result.status == HttpStatusCode.OK) {
                    val resultBody = result.body<List<FileResponse>>()
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