package com.justparokq.homeftp.shared.ftp.model

data class LocalFile(
    val name: String,
    val mimeType: String,
    val byteContent: ByteArray
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as LocalFile

        if (name != other.name) return false
        if (mimeType != other.mimeType) return false
        if (!byteContent.contentEquals(other.byteContent)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + mimeType.hashCode()
        result = 31 * result + byteContent.contentHashCode()
        return result
    }
}