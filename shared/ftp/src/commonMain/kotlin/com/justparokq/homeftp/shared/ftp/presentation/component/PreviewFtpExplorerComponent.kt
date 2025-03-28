package com.justparokq.homeftp.shared.ftp.presentation.component

import com.arkivanov.decompose.value.Value
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.FtpExplorerScreenModel
import io.github.vinceglb.filekit.core.PlatformFile

object PreviewFtpExplorerComponent : FtpExplorerComponent {
    override val state: Value<FtpExplorerScreenModel>
        get() = TODO("Not yet implemented")

    override fun onDirectoryClicked(dirPath: List<String>) {}
    override fun onFileSystemObjectClicked(fsObject: FileSystemObject) {}
    override fun onFloatingButtonClicked() {}
    override fun onFilesPicked(files: List<PlatformFile>) {}
    override fun onNavigateBackClicked() {}
}
