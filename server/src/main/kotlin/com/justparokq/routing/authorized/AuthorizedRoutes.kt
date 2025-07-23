package com.justparokq.routing.authorized

import Dependencies
import io.ktor.server.routing.Route

internal fun Route.addJWTAuthorizedRoutes(isTest: Boolean) {
    val ftpDependencies = Dependencies.getFtpDependencies(isTest)
    addFtpRoutes(ftpDependencies)
}