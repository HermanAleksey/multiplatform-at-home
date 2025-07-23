package com.justparokq.login.data

import java.util.*
import java.util.concurrent.ConcurrentHashMap

interface RefreshTokenRepository {
    fun createToken(username: String, expiresInMillis: Long): String
    fun validateAndInvalidate(token: String): String?
    fun invalidate(token: String)
}

class ProdRefreshTokenRepository : RefreshTokenRepository {
    data class RefreshTokenInfo(val username: String, val expiresAt: Long)
    private val tokens = ConcurrentHashMap<String, RefreshTokenInfo>()

    override fun createToken(username: String, expiresInMillis: Long): String {
        val token = UUID.randomUUID().toString()
        val expiresAt = System.currentTimeMillis() + expiresInMillis
        tokens[token] = RefreshTokenInfo(username, expiresAt)
        return token
    }

    override fun validateAndInvalidate(token: String): String? {
        val info = tokens[token]
        if (info != null && info.expiresAt > System.currentTimeMillis()) {
            tokens.remove(token)
            return info.username
        }
        tokens.remove(token)
        return null
    }

    override fun invalidate(token: String) {
        tokens.remove(token)
    }
}

class TestRefreshTokenRepository : RefreshTokenRepository {
    data class RefreshTokenInfo(val username: String, val expiresAt: Long)
    private val tokens = ConcurrentHashMap<String, RefreshTokenInfo>()

    override fun createToken(username: String, expiresInMillis: Long): String {
        val token = UUID.randomUUID().toString()
        val expiresAt = System.currentTimeMillis() + expiresInMillis
        tokens[token] = RefreshTokenInfo(username, expiresAt)
        return token
    }

    override fun validateAndInvalidate(token: String): String? {
        val info = tokens[token]
        if (info != null && info.expiresAt > System.currentTimeMillis()) {
            tokens.remove(token)
            return info.username
        }
        tokens.remove(token)
        return null
    }

    override fun invalidate(token: String) {
        tokens.remove(token)
    }
}

object RefreshTokenRepositoryFactory {
    val prod: RefreshTokenRepository = ProdRefreshTokenRepository()
    val test: RefreshTokenRepository = TestRefreshTokenRepository()
    fun get(isTest: Boolean): RefreshTokenRepository = if (isTest) test else prod
} 