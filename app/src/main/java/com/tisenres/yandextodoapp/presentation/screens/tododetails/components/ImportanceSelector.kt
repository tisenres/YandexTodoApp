package com.tisenres.yandextodoapp.presentation.screens.tododetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tisenres.yandextodoapp.R
import com.tisenres.yandextodoapp.domain.entity.Importance

@Composable
fun ImportanceSelector(
    selectedImportance: Importance,
    onImportanceSelected: (Importance) -> Unit
) {
    val importanceOptions = listOf(Importance.LOW, Importance.NORMAL, Importance.HIGH)
    val importanceIcons = mapOf(
        Importance.LOW to R.drawable.priority_low,
        Importance.NORMAL to R.drawable.no_label,
        Importance.HIGH to R.drawable.priority_high
    )

    val importanceColors = mapOf(
        Importance.LOW to MaterialTheme.colorScheme.onSurfaceVariant,
        Importance.NORMAL to MaterialTheme.colorScheme.onSurface,
        Importance.HIGH to MaterialTheme.colorScheme.error
    )

    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        importanceOptions.forEach { option ->
            val icon = importanceIcons[option]
            val color = importanceColors[option] ?: MaterialTheme.colorScheme.onSurface
            val isSelected = option == selectedImportance

            val backgroundColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }

            val contentColor = if (isSelected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                color
            }

            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onImportanceSelected(option) },
                shape = shapes.small,
                color = backgroundColor,
                contentColor = contentColor
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    if (icon != null) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}