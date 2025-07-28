package com.justparokq.homeftp.shared.ftp.model

import kotlin.jvm.JvmInline

@JvmInline
value class Path(val raw: String) {

    val parts: List<String>
        get() = raw.trim('/').split('/').filter { it.isNotBlank() }

    fun parent(): Path? =
        if (parts.isEmpty()) null
        else Path("/" + parts.dropLast(1).joinToString("/"))

    fun child(name: String): Path =
        Path("/" + (parts + name).joinToString("/"))

    fun resolveTo(index: Int): Path =
        Path("/" + parts.take(index + 1).joinToString("/"))

    override fun toString(): String = raw

    companion object Companion {
        val Root = Path("/")
    }
}