package com.tisenres.yandextodoapp.presentation.screens.tododetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tisenres.yandextodoapp.R
import com.tisenres.yandextodoapp.domain.entity.Importance

@Composable
fun ImportanceSelector(
    selectedImportance: Importance,
    onImportanceSelected: (Importance) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val importanceOptions = listOf(Importance.LOW, Importance.NORMAL, Importance.HIGH)
    val importanceLabels = mapOf(
        Importance.LOW to "Низкий",
        Importance.NORMAL to "Нет",
        Importance.HIGH to "Высокий"
    )
    val importanceIcons = mapOf(
        Importance.LOW to R.drawable.priority_low,
        Importance.NORMAL to R.drawable.no_label,
        Importance.HIGH to R.drawable.priority_high
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            val icon = importanceIcons[selectedImportance]
            val label = importanceLabels[selectedImportance] ?: "Нет"

            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            importanceOptions.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onImportanceSelected(option)
                        expanded = false
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val optionIcon = importanceIcons[option]
                            val optionLabel = importanceLabels[option] ?: "Нет"

                            if (optionIcon != null) {
                                Icon(
                                    painter = painterResource(id = optionIcon),
                                    contentDescription = optionLabel,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                            }
                            Text(
                                text = optionLabel,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                )
            }
        }
    }
}