package com.justparokq.homeftp.shared.ftp.presentation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.justparokq.homeftp.shared.common.Result
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponent
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerComponentIntent
import com.justparokq.homeftp.shared.ftp.api.FtpExplorerScreenModel
import com.justparokq.homeftp.shared.ftp.api.OnDirectoryClicked
import com.justparokq.homeftp.shared.ftp.api.OnFileSystemObjectClicked
import com.justparokq.homeftp.shared.ftp.api.OnFilesPicked
import com.justparokq.homeftp.shared.ftp.api.OnFloatingButtonClicked
import com.justparokq.homeftp.shared.ftp.api.OnNavigateBackClicked
import com.justparokq.homeftp.shared.ftp.api.OnSortingApplyClicked
import com.justparokq.homeftp.shared.ftp.data.mapper.FileSystemObjectMapper
import com.justparokq.homeftp.shared.ftp.data.network.FtpCommunicationHttpClient
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import kotlinx.coroutines.launch

internal class DefaultFtpExplorerComponent(
    componentContext: ComponentContext,
    private val ftpHttpClient: FtpCommunicationHttpClient,
    private val fileSystemObjectMapper: FileSystemObjectMapper,
) : FtpExplorerComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

    private val _state = MutableValue(FtpExplorerScreenModel())
    override val state: Value<FtpExplorerScreenModel> = _state

    init {
        val serverFtpRootUri = ""
        load(serverFtpRootUri)
    }

    override fun processIntent(intent: FtpExplorerComponentIntent) {
        when (intent) {
            is OnDirectoryClicked -> onDirectoryClicked(intent.dirPath)
            is OnFileSystemObjectClicked -> onFileSystemObjectClicked(intent.fsObject)
            is OnFilesPicked -> Unit // TODO()
            OnFloatingButtonClicked -> Unit // TODO()
            OnNavigateBackClicked -> onNavigateBackClicked()
            OnSortingApplyClicked -> Unit // TODO()
        }
    }

    private fun onDirectoryClicked(dirPath: List<String>) {
        _state.update {
            it.copy(
                currentPath = dirPath
            )
        }
        load(state.value.getCurrentPathAsString())
    }

    private fun onFileSystemObjectClicked(fsObject: FileSystemObject) {
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

    private fun onNavigateBackClicked() {
        val newPath = _state.value.currentPath
            .takeIf { it.isNotEmpty() }
            ?.dropLast(1)
            ?: _state.value.currentPath
        onDirectoryClicked(newPath)
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
}
