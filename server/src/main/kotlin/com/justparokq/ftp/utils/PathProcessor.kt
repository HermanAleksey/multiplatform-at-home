package com.justparokq.ftp.utils

internal class PathProcessor(
    private val rootPath: String = PathConstants.DEFAULT_ROOT_STORAGE_PATH,
    private val initialContentPath: String? = null
) {

    init {
        // check if rootPath either exists and is a folder either doesn't exist at all
        val rootDirCheck = java.io.File(rootPath)
        require(rootDirCheck.exists() && rootDirCheck.isDirectory || !rootDirCheck.exists()) {
            "Provided rootPath '$rootPath' is not a valid directory."
        }
        if (!rootDirCheck.exists()) {
            rootDirCheck.mkdirs()
        }


        if (initialContentPath.isNullOrBlank().not()) {
            // remove all existing files in selected [rootPath] directory,
            // then put everything from [initialContentPath]
            replaceContentInDirectory()
        }
    }

    private fun replaceContentInDirectory() {
        val rootDir = java.io.File(rootPath)
        val initialContentDir = initialContentPath?.let { java.io.File(it) }

        // Remove all files and directories in rootPath
        if (rootDir.exists() && rootDir.isDirectory) {
            rootDir.listFiles()?.forEach { file ->
                file.deleteRecursively()
            }
        } else {
            rootDir.mkdirs()
        }

        // Copy everything from initialContentPath to rootPath
        if (initialContentDir != null && initialContentDir.exists() && initialContentDir.isDirectory) {
            initialContentDir.listFiles()?.forEach { file ->
                val dest = java.io.File(rootDir, file.name)
                file.copyRecursively(dest, overwrite = true)
            }
        }
    }

    fun getRootDirectoryPath(): String = rootPath

    fun getPreviewRootDirectoryPath(): String = rootPath + "previews/"
}