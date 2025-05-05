package com.justparokq.homeftp.shared.ftp.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.justparokq.homeftp.theme.AppTheme
import com.justparokq.homeftp.tooling.Preview

@Composable
internal fun CurrentPathLine(
    path: List<String>,
    onPathPartClicked: (list: List<String>) -> Unit,
    onNavigateBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        BackButton(
            onBackClicked = { onNavigateBackClicked() },
            Modifier.padding(horizontal = 16.dp)
        )
        LazyRow(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            path.forEachIndexed { index, str ->
                item {
                    PathElement("$str/") {
                        onPathPartClicked(path.subList(0, index + 1))
                    }
                }
            }
        }
    }
}

@Composable
internal fun BackButton(onBackClicked: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { onBackClicked() },
        modifier = modifier.size(24.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Назад",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
internal fun PathElement(text: String, onItemClicked: (String) -> Unit) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
            .padding(4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(3.dp)
                .clickable {
                    onItemClicked(text)
                }
        )
    }
}

@Preview
@Composable
private fun DirectoriesPreview() {
    AppTheme {
        val path = listOf("LOL", "hi", "nagget", "yo_gay", "root", "documents", "images")
        CurrentPathLine(path, {}, {})
    }
}