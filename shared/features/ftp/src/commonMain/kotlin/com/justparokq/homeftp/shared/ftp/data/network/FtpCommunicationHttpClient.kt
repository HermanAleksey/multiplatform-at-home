package com.justparokq.homeftp.shared.ftp.data.network

import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.ftp.FileResponse
import com.justparokq.homeftp.shared.utils.localhostUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

internal interface FtpCommunicationHttpClient {

    fun getDirectoryContent(directoryUri: String): Flow<Result<List<FileResponse>>>
}

internal class FtpCommunicationHttpClientImpl : FtpCommunicationHttpClient {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    // request to http://0.0.0.0:8080/directory?path=directoryUri
    override fun getDirectoryContent(
        directoryUri: String,
    ): Flow<Result<List<FileResponse>>> {
        println("TAGG Request: $directoryUri")
        return flow {
            emit(Result.Loading(true))
            try {
                val url = URLBuilder(
                    host = localhostUrl,
                    port = 8080,
                    pathSegments = listOf("directory"),
                ).build()

                val result = httpClient.request(url) {
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