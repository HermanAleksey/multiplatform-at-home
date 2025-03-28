package com.justparokq.homeftp.shared.ftp.model

data class FtpExplorerScreenModel(
    val currentPath: List<String> = emptyList(),
    val fsObjects: List<FileSystemObject> = emptyList(),
    val isLoading: Boolean = true,
) {

    fun getCurrentPathAsString(): String {
        return currentPath.joinToString("/")
    }
}