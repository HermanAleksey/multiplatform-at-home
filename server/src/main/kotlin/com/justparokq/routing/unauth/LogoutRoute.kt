package com.justparokq.routing.unauth

import Dependencies
import com.justparokq.homeftp.shared.login.LogoutRequest
import com.justparokq.homeftp.shared.login.LogoutResponse
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext

internal suspend fun RoutingContext.addLogoutRoute(
    isTest: Boolean
) {
    // Invalidate refresh token if provided
    val refreshRequest = call.receive<LogoutRequest>()
    val refreshToken = refreshRequest.refreshToken
    val tokenService = Dependencies.getTokenService()
    tokenService.validateAndInvalidateRefreshToken(refreshToken, isTest)
    // For JWT, logout is stateless; just respond OK
    call.respond(LogoutResponse(success = true, message = "Logged out"))
}