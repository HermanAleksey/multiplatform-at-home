package com.justparokq.homeftp.shared.main.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.justparokq.homeftp.shared.main.api.Default
import com.justparokq.homeftp.shared.main.api.MainComponent
import com.justparokq.homeftp.shared.main.api.OnFeatureClicked

@Composable
internal fun InternalMainContent(
    component: MainComponent,
) {
    val stateValue = if (component.state.value is Default)
        component.state.value as Default
    else return

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(stateValue.features) { featureItem ->
            if (featureItem.isEnabled) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(80.dp)
                        .background(Color.Cyan)
                        .clickable { component.processIntent(OnFeatureClicked(featureItem)) },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(featureItem.name)
                }
            }
        }
    }
}