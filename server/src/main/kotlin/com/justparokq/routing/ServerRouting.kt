package com.justparokq.routing

import com.auth0.jwk.JwkProvider
import com.justparokq.routing.authorized.addJWTAuthorizedRoutes
import com.justparokq.routing.unauth.addUnauthorizedRoutes
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.bearer
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.http.content.resource
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

const val JWT_AUTH_CONFIG = "auth-jwt"
const val TEST_JWT_AUTH_CONFIG = "test-auth-jwt"

fun Application.serverRouting(jwkProvider: JwkProvider) {
    install(ContentNegotiation) {
        json()
    }

    // reading from application.conf
    val privateKeyString = environment.config.property("jwt.privateKey").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    installJWTAuthentication(myRealm, jwkProvider, issuer)

    routing {
        addCommonRoutes(jwkProvider, privateKeyString, audience, issuer, isTest = false)

        route("/test") {
            addCommonRoutes(jwkProvider, privateKeyString, audience, issuer, isTest = true)
        }

        route(".well-known") {
            resource("jwks.json")
        }
    }
}

private fun Route.addCommonRoutes(
    jwkProvider: JwkProvider,
    privateKeyString: String,
    audience: String,
    issuer: String,
    isTest: Boolean
) {
    addUnauthorizedRoutes(
        jwkProvider = jwkProvider,
        privateKeyString = privateKeyString,
        audience = audience,
        issuer = issuer,
        isTest = isTest
    )

    authenticate(if (isTest) TEST_JWT_AUTH_CONFIG else JWT_AUTH_CONFIG) {
        addJWTAuthorizedRoutes(isTest)
    }
}

internal fun Application.installJWTAuthentication(
    myRealm: String,
    jwkProvider: JwkProvider,
    issuer: String,
) {
    install(Authentication) {
        bearer {

        }
        jwt(JWT_AUTH_CONFIG) {
            realm = myRealm
            verifier(jwkProvider, issuer) {
                acceptLeeway(3)
            }
            validate { credential ->
                if (credential.payload.getClaim("username").asString().isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
        //
        jwt(TEST_JWT_AUTH_CONFIG) {
            realm = myRealm
            verifier(jwkProvider, issuer) {
                acceptLeeway(3)
//                withKeyId("6f8856ed-9189-488f-9011-0ff4b6c08edc")
            }
            validate { credential ->
                if (credential.payload.getClaim("username").asString().isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}