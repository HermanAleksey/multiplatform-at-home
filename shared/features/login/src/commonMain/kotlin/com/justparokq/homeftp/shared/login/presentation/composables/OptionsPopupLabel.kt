package com.justparokq.homeftp.shared.login.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.justparokq.homeftp.shared.core.setting_key.Setting
import com.justparokq.homeftp.shared.login.api.Active

/**
 * Label that opens a popup with network options when clicked.
 * Selecting an option dismisses the popup.
 */
@Composable
internal fun OptionsPopupLabel(
    networkOptionState: Active.NetworkOptionState,
    onOptionSelected: (Setting.NetworkKey.Target.Option) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(networkOptionState.selectedOption) }
    var pendingSelection by remember { mutableStateOf(selectedOption) }

    Column(modifier = modifier) {
        Text(
            text = "Not user agreement",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    pendingSelection = selectedOption
                    expanded = true
                }
                .padding(8.dp)
        )
        if (expanded) {
            NetworkOptionsDialog(
                networkOptions = networkOptionState.networkOptions,
                selectedOption = pendingSelection,
                onOptionSelected = { option ->
                    selectedOption = option
                    expanded = false
                    onOptionSelected(option)
                },
                onDismiss = { expanded = false }
            )
        }
    }
}

/**
 * Dialog for selecting network options.
 */
@Composable
private fun NetworkOptionsDialog(
    networkOptions: List<Setting.NetworkKey.Target.Option>,
    selectedOption: Setting.NetworkKey.Target.Option,
    onOptionSelected: (Setting.NetworkKey.Target.Option) -> Unit,
    onDismiss: () -> Unit,
) {
    var pendingSelection by remember { mutableStateOf(selectedOption) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Box(modifier = Modifier.widthIn(max = 320.dp)) {
                Text(text = "Choose an option", style = MaterialTheme.typography.titleLarge)
            }
        },
        text = {
            Box(modifier = Modifier.widthIn(max = 320.dp)) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .heightIn(max = 240.dp)
                ) {
                    networkOptions.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { pendingSelection = option }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = pendingSelection == option,
                                onClick = { pendingSelection = option }
                            )
                            Text(
                                text = option.name.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onOptionSelected(pendingSelection) }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 