package com.justparokq.routing.unauth

import com.auth0.jwk.JwkProvider
import com.justparokq.login.data.LoginRequestMapper
import com.justparokq.login.data.UserRepository
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

internal fun Route.addUnauthorizedRoutes(
    jwkProvider: JwkProvider,
    privateKeyString: String,
    audience: String,
    issuer: String,
    isTest: Boolean,
) {
    post("/login") {
        addLoginRoute(
            jwkProvider,
            privateKeyString,
            audience,
            issuer,
            isTest
        )
    }

    // Allow refresh only from trusted clients (e.g., check device, IP, or fingerprint).
    post("/refresh") {
        addRefreshRoute(jwkProvider, privateKeyString, audience, issuer, isTest)
    }

    post("/logout") {
        addLogoutRoute(isTest)
    }
}
