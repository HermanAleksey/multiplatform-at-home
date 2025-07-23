package com.justparokq.homeftp.shared.ftp.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.justparokq.homeftp.shared.ftp.model.FileSystemObject
import com.justparokq.homeftp.theme.AppTheme
import com.justparokq.homeftp.tooling.Preview
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

private const val FILES_IN_LINE_DEFAULT_NUMBER = 3

@Composable
internal fun FtpScreen(
    path: List<String>,
    fsObjects: List<FileSystemObject>,
    onPathPartClicked: (List<String>) -> Unit,
    onFSObjectClicked: (FileSystemObject) -> Unit,
    onNavigateBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        CurrentPathLine(
            path = path,
            onPathPartClicked = onPathPartClicked,
            onNavigateBackClicked = onNavigateBackClicked,
            modifier = Modifier.fillMaxWidth(),
        )
        GalleryView(
            fileSystemObjects = fsObjects,
            onFSObjectClicked = onFSObjectClicked,
            modifier = Modifier.fillMaxSize()
        )
        Spacer(modifier = Modifier.height(80.dp))
    }
}


@Composable
internal fun GalleryView(
    fileSystemObjects: List<FileSystemObject>,
    onFSObjectClicked: (FileSystemObject) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        fileSystemObjects.chunked(FILES_IN_LINE_DEFAULT_NUMBER).forEach { rowOfFiles ->
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 1.dp)
                ) {
                    repeat(FILES_IN_LINE_DEFAULT_NUMBER) { index ->
                        Spacer(modifier = Modifier.width(1.dp))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .then(if (index < rowOfFiles.size) Modifier else Modifier.alpha(0f))
                                .background(Color.Gray)
                        ) {
                            if (index < rowOfFiles.size) {
                                FileElement(
                                    file = rowOfFiles[index],
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            onFSObjectClicked(rowOfFiles[index])
                                        }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(1.dp))
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(2.dp)) }
        }
    }
}

@Composable
internal fun FileElement(
    file: FileSystemObject,
    modifier: Modifier = Modifier,
) {
    when (file) {
        is FileSystemObject.Directory -> {
            Column(modifier) {
                Icon(
                    imageVector = Icons.Filled.Folder,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = file.name,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        is FileSystemObject.File.Image -> {
            CoilImage(
                imageModel = { file.getFullUrl() },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                modifier = Modifier.fillMaxSize()
            )
        }

        is FileSystemObject.File.Video -> Text("Videos are not supported yet", modifier)
        FileSystemObject.File.Unknown -> Text("Unknown file extension", modifier)
    }
}

@Preview
@Composable
private fun FtpScreenPreview() {
    val currentPath = listOf("LOL", "hi", "yo_gay", "root", "documents", "images")
    val files = listOf(
        FileSystemObject.File.Image(name = "Image 1", remotePath = "23123"),
        FileSystemObject.File.Image(name = "Video 2", remotePath = "23123"),
        FileSystemObject.Directory(name = "fldr", content = emptyList()),
        FileSystemObject.File.Unknown,
        FileSystemObject.File.Image(name = "Image 4", remotePath = "23123"),
        FileSystemObject.Directory(name = "Folder 2", content = emptyList()),
        FileSystemObject.File.Image(name = "Image 5!", remotePath = "23123"),
        FileSystemObject.Directory(name = "Folder 3", content = emptyList()),
    )

    AppTheme {
        FtpScreen(
            path = currentPath,
            fsObjects = files,
            onPathPartClicked = {},
            onFSObjectClicked = {},
            onNavigateBackClicked = {},
            modifier = Modifier
        )
    }
}