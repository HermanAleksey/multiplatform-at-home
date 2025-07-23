package com.justparokq.homeftp.shared.main.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Unpublished
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.justparokq.homeftp.shared.main.api.Default
import com.justparokq.homeftp.shared.main.api.MainComponent
import com.justparokq.homeftp.shared.main.api.OnFeatureClicked
import com.justparokq.homeftp.shared.main.domain.FeatureParamsModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
internal fun InternalMainContent(
    component: MainComponent,
) {
    val subscribedState = component.state.subscribeAsState()
    require(subscribedState.value is Default)

    when (val value = subscribedState.value) {
        is Default -> LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            items(value.features) { featureItem ->
                if (featureItem.isEnabled) {
                    FeatureCard(
                        featureItem = featureItem,
                        onFeatureClicked = { component.processIntent(OnFeatureClicked(featureItem)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureCard(
    featureItem: FeatureParamsModel,
    onFeatureClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onFeatureClicked() },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            FeatureImage(featureItem.imageUrl)
            FeatureContent(featureItem)
        }
    }
}

@Composable
private fun FeatureImage(imageUrl: String?) {
    if (imageUrl != null) {
        CoilImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                alignment = Alignment.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(
                    RoundedCornerShape(
                        topStartPercent = 16,
                        topEndPercent = 16,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                )
        )
    } else {
        FeatureImagePlaceholder()
    }
}

@Composable
private fun FeatureImagePlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(
                RoundedCornerShape(
                    topStartPercent = 16,
                    topEndPercent = 16,
                    bottomStartPercent = 0,
                    bottomEndPercent = 0
                )
            )
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(
                    topStartPercent = 16,
                    topEndPercent = 16,
                    bottomStartPercent = 0,
                    bottomEndPercent = 0
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Unpublished,
            contentDescription = "Feature placeholder",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun FeatureContent(featureItem: FeatureParamsModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = featureItem.key.key,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Available feature",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}