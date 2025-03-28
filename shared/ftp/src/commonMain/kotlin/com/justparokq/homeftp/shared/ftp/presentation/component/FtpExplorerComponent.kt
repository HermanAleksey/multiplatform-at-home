package com.justparokq.homeftp.shared.ftp.presentation.component

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.FtpExplorerScreenModel
import io.github.vinceglb.filekit.core.PlatformFile

interface FtpExplorerComponent {

    val state: Value<FtpExplorerScreenModel>

    /**
     * Navigates to directory by path
     */
    fun onDirectoryClicked(dirPath: List<String>)

    /**
     * Open fsObject
     * If file is Directory - navigates to it
     * If file is File - opens it (photo or video or smt else)
     * */
    fun onFileSystemObjectClicked(fsObject: FileSystemObject)

    /**
     * Not for MVP
     * Apply sorting to all files in MainView (currently opened directory)
     * This setting should persist between directories?
     * */
    fun onSortingApplyClicked() {}

    /**
     * Floating button meant to open file picker
     * Selected file(s) are uploaded to server to the current folder
     * */
    fun onFloatingButtonClicked()

    /**
     * Invoke when user selected one of few files from gallery
     * */
    fun onFilesPicked(files: List<PlatformFile>)

    fun onNavigateBackClicked()
}
