package com.justparokq.homeftp.shared.ftp.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.ftp.data.mapper.FileSystemObjectMapper
import com.justparokq.homeftp.shared.ftp.data.mapper.FileSystemObjectMapperImpl
import com.justparokq.homeftp.shared.ftp.data.network.FtpCommunicationHttpClient
import com.justparokq.homeftp.shared.ftp.data.network.FtpCommunicationHttpClientImpl
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.FtpExplorerScreenModel
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import io.github.vinceglb.filekit.core.PlatformFile
import kotlinx.coroutines.launch

class DefaultFtpExplorerComponent(
    componentContext: ComponentContext,
    private val ftpHttpClient: FtpCommunicationHttpClient = FtpCommunicationHttpClientImpl(),
    private val fileSystemObjectMapper: FileSystemObjectMapper = FileSystemObjectMapperImpl(),
) : FtpExplorerComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    private val _state = MutableValue(FtpExplorerScreenModel())
    override val state: Value<FtpExplorerScreenModel> = _state

    init {
        val serverFtpRootUri = ""
        load(serverFtpRootUri)
    }

    private fun load(uriToLoad: String) {
        coroutineScope.launch {
            ftpHttpClient.getDirectoryContent(uriToLoad)
                .collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _state.update {
                                it.copy(isLoading = result.loading)
                            }
                        }

                        is Result.Error -> {
                            _state.update {
                                it.copy(isLoading = false)
                            }
                            println("DefaultFtpExplorerComponent; Show error: ${result.errorMessage}")
                        }

                        is Result.Success -> {
                            val domainObjects = result.result.map {
                                fileSystemObjectMapper.toFileSystemObject(it)
                            }
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    fsObjects = domainObjects
                                )
                            }
                        }
                    }
                }
        }

    }

    override fun onDirectoryClicked(dirPath: List<String>) {
        _state.update {
            it.copy(
                currentPath = dirPath
            )
        }
        load(state.value.getCurrentPathAsString())
    }

    override fun onFileSystemObjectClicked(fsObject: FileSystemObject) {
        when (fsObject) {
            is FileSystemObject.Directory -> {
                val newPath = _state.value.currentPath + fsObject.name
                onDirectoryClicked(newPath)
            }

            is FileSystemObject.File.Image -> {
                // todo open full screen photo
            }

            FileSystemObject.File.Unknown -> {
                // todo show toast
            }

            is FileSystemObject.File.Video -> {
                // todo open full screen video
            }
        }
    }

    override fun onFloatingButtonClicked() {
        TODO("Not yet implemented")
    }

    override fun onFilesPicked(files: List<PlatformFile>) {
        files.forEach { file ->
            file.name
        }
//        TODO("Not yet implemented")
    }

    override fun onNavigateBackClicked() {
        val newPath = _state.value.currentPath
            .takeIf { it.isNotEmpty() }
            ?.dropLast(1)
            ?: _state.value.currentPath
        onDirectoryClicked(newPath)
    }
}
