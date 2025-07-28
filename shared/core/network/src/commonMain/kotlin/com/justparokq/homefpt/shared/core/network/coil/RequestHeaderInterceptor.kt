package com.justparokq.homefpt.shared.core.network.coil

import coil3.intercept.Interceptor
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import coil3.request.ImageResult
import com.justparokq.homefpt.shared.core.network.Const.AUTH_HEADER
import com.justparokq.homefpt.shared.core.network.Const.CACHE_CONTROL_HEADER
import com.justparokq.homeftp.shared.core.setting_store.NetworkStore

class RequestHeaderInterceptor(
    private val networkStore: NetworkStore,
) : Interceptor {

    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val accessToken = networkStore.getAccessToken()
        val headers = NetworkHeaders.Builder()
            .set(CACHE_CONTROL_HEADER, "no-cache")
            .set(AUTH_HEADER, "Bearer $accessToken")
            .build()

        val request: ImageRequest = chain.request.newBuilder()
            .httpHeaders(headers)
            .build()

        return chain.withRequest(request).proceed()
    }
}