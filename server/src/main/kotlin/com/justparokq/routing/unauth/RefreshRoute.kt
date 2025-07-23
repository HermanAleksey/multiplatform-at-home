package com.justparokq.routing.unauth

import Dependencies
import com.auth0.jwk.JwkProvider
import com.justparokq.homeftp.shared.login.RefreshRequest
import com.justparokq.homeftp.shared.login.RefreshResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext

internal suspend fun RoutingContext.addRefreshRoute(
    jwkProvider: JwkProvider,
    privateKeyString: String,
    audience: String,
    issuer: String,
    isTest: Boolean,
) {
    val tokenService = Dependencies.getTokenService()
    val refreshRequest = call.receive<RefreshRequest>()
    val refreshToken = refreshRequest.refreshToken
    val username = tokenService.validateAndInvalidateRefreshToken(refreshToken, isTest)
    if (username == null) {
        return call.respond(HttpStatusCode.Unauthorized, "Invalid or expired refresh token")
    }
    val (publicKey, privateKey) = tokenService.extractKeys(jwkProvider, privateKeyString)
    val expiresIn = if (isTest) TEST_TOKEN_EXPIRATION_TIME else PROD_TOKEN_EXPIRATION_TIME
    val token = tokenService.generateAccessToken(
        username = username,
        audience = audience,
        issuer = issuer,
        expiresInMillis = expiresIn,
        publicKey = publicKey,
        privateKey = privateKey
    )
    val newRefreshToken = tokenService.generateRefreshToken(
        username = username,
        expiresInMillis = REFRESH_TOKEN_EXPIRATION_TIME,
        isTest = isTest
    )

    call.respond(RefreshResponse(token = token, refreshToken = newRefreshToken))
}