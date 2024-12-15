package com.justparokq.homeftp

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.justparokq.homeftp.features.main.composables.FileSystemHierarchy
import com.justparokq.homeftp.shared.ftp.presentation.FtpExplorerComponent
import com.justparokq.homeftp.shared.ftp.presentation.PreviewFtpExplorerComponent
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FtpContent(
    component: FtpExplorerComponent,
    modifier: Modifier = Modifier,
) {
    val launcher = rememberFilePickerLauncher(
        type = PickerType.ImageAndVideo,
        mode = PickerMode.Multiple(),
        title = "Pick a media",
//        initialDirectory = "/custom/initial/path"
    ) { files ->
        files ?: return@rememberFilePickerLauncher
        component.onFilesPicked(files)
    }

    val state = component.state.subscribeAsState()
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Client", style = MaterialTheme.typography.headlineMedium) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                launcher.launch()
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Select file to upload"
                )
            }
        }
    ) { paddings ->
        Column(
            modifier = Modifier.padding(paddings)
                .background(MaterialTheme.colorScheme.background)
        ) {
            FileSystemHierarchy(
                fileHierarchy = state.value.fileTree,
                onFileClicked = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    FtpContent(PreviewFtpExplorerComponent)
}
