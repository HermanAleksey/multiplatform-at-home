package com.justparokq.homeftp.shared.features.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.justparokq.homeftp.shared.features.settings.data.DatabaseObject
import com.justparokq.homeftp.shared.features.settings.presentation.component.SettingsComponent
import com.justparokq.homeftp.shared.features.settings.presentation.model.SettingsScreenModel
import com.justparokq.homeftp.shared.utils.ContextFactoryComposition
import com.justpoarokq.shared.core.base_database.model.SettingModel

@Composable
fun SettingsContent(component: SettingsComponent) {
    val context = ContextFactoryComposition.current.getContext()
    LaunchedEffect(Unit) {
        DatabaseObject.init(context)
        component.onDatabaseInitialized()
    }
    val state = component.state.subscribeAsState()
    SettingsContentScreen(
        settingModel = state.value,
        onSettingChanged = { component.onSettingChanged(it) }
    )
}

@Composable
private fun SettingsContentScreen(
    settingModel: SettingsScreenModel,
    onSettingChanged: (SettingModel) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Network",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        settingModel.networkSettings.forEach {
            NotificationSettingItem(
                settingModel = it,
                onSettingChanged = onSettingChanged,
            )
        }

        Text(
            text = "This is where you'll receive notifications",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        settingModel.featureSettings.forEach {
            NotificationSettingItem(
                settingModel = it,
                onSettingChanged = onSettingChanged,
            )
        }
    }
}

@Composable
private fun NotificationSettingItem(
    settingModel: SettingModel,
    onSettingChanged: (SettingModel) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = settingModel.name,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = settingModel.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            when (val settingValue = settingModel.value) {
                is SettingModel.Value.Boolean -> Switch(
                    checked = settingValue.value,
                    onCheckedChange = { checked ->
                        onSettingChanged(
                            settingModel.copy(
                                value = SettingModel.Value.Boolean(checked)
                            )
                        )
                    }
                )

                else -> Unit // Для других типов настроек
            }
        }
    }
}
