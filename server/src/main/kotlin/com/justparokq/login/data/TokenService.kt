package com.justparokq.login.data

import Dependencies
import com.auth0.jwk.JwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
import java.util.Date

internal class TokenService {

    fun extractKeys(
        jwkProvider: JwkProvider,
        privateKeyString: String
    ): Pair<RSAPublicKey, RSAPrivateKey> {
        val publicKey =
            jwkProvider.get("6f8856ed-9189-488f-9011-0ff4b6c08edc").publicKey as RSAPublicKey
        val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString))
        val privateKey =
            KeyFactory.getInstance("RSA").generatePrivate(keySpecPKCS8) as RSAPrivateKey
        return publicKey to privateKey
    }

    fun generateAccessToken(
        username: String,
        audience: String,
        issuer: String,
        expiresInMillis: Long,
        publicKey: RSAPublicKey,
        privateKey: RSAPrivateKey
    ): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", username)
            .withExpiresAt(Date(System.currentTimeMillis() + expiresInMillis))
            .sign(Algorithm.RSA256(publicKey, privateKey))
    }

    fun generateRefreshToken(
        username: String,
        expiresInMillis: Long,
        isTest: Boolean
    ): String {
        val repo = Dependencies.getRefreshTokenRepository(isTest)
        return repo.createToken(username, expiresInMillis)
    }

    fun validateAndInvalidateRefreshToken(
        refreshToken: String,
        isTest: Boolean
    ): String? {
        val repo = Dependencies.getRefreshTokenRepository(isTest)
        return repo.validateAndInvalidate(refreshToken)
    }
} 