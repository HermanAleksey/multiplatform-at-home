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
import com.justparokq.homeftp.shared.ftp.api.OnRefreshPulled
import com.justparokq.homeftp.shared.ftp.api.OnScreenOpened
import com.justparokq.homeftp.shared.ftp.api.OnSortingApplyClicked
import com.justparokq.homeftp.shared.ftp.data.mapper.FileSystemObjectMapper
import com.justparokq.homeftp.shared.ftp.data.network.FtpCommunicationHttpClient
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.shared.ftp.model.Path
import com.justparokq.homeftp.shared.navigation.acrhitecture.InitHelper
import com.justparokq.homeftp.shared.navigation.feature.FeatureNavigator
import com.justparokq.homeftp.shared.utils.componentCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class DefaultFtpExplorerComponent(
    componentContext: ComponentContext,
    private val ftpHttpClient: FtpCommunicationHttpClient,
    private val fileSystemObjectMapper: FileSystemObjectMapper,
    private val featureNavigator: FeatureNavigator,
    initHelper: InitHelper,
) : FtpExplorerComponent, ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()
    private var loadDataJob: Job? = null

    private val _state = MutableValue(FtpExplorerScreenModel())
    override val state: Value<FtpExplorerScreenModel> = _state

    init {
        initHelper(intentProcessor = ::processIntent, intent = OnScreenOpened)
    }

    override fun processIntent(intent: FtpExplorerComponentIntent) {
        when (intent) {
            OnScreenOpened -> loadDataFromPath(state.value.currentPath)
            is OnDirectoryClicked -> onDirectoryClicked(intent.dirPath)
            is OnFileSystemObjectClicked -> onFileSystemObjectClicked(intent.fsObject)
            is OnFilesPicked -> Unit // TODO()
            OnSortingApplyClicked -> Unit // TODO()
            is OnFloatingButtonClicked -> Unit // TODO()
            OnRefreshPulled -> Unit // TODO()
            OnNavigateBackClicked -> onNavigateBackClicked()
        }
    }

    private fun onDirectoryClicked(dirPath: Path) {
        _state.update {
            it.copy(currentPath = dirPath)
        }
        loadDataFromPath(state.value.currentPath)
    }

    private fun onFileSystemObjectClicked(fsObject: FileSystemObject) {
        when (fsObject) {
            is FileSystemObject.Directory -> {
                onDirectoryClicked(fsObject.fullPath)
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
        val parentDirectory = _state.value.currentPath.parent()
        if (parentDirectory == null) {
            // we are at the root, leave screen
            featureNavigator.popBackStack()
        } else {
            onDirectoryClicked(parentDirectory)
        }
    }


    private fun loadDataFromPath(uriToLoad: Path) {
        loadDataJob?.cancel()

        loadDataJob = coroutineScope.launch {
            ftpHttpClient.getDirectoryContent(uriToLoad.raw)
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

    override fun processBackInput(): Boolean {
        onNavigateBackClicked()
        return true
    }
}
