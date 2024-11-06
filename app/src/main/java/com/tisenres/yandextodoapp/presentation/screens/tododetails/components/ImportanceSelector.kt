package com.tisenres.yandextodoapp.presentation.screens.tododetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tisenres.yandextodoapp.R
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.presentation.theme.LocalExtendedColors

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
        Importance.LOW to Color.Gray,
        Importance.NORMAL to Color.Black,
        Importance.HIGH to Color.Red
    )

    Row(
        modifier = Modifier
            .background(LocalExtendedColors.current.tertiaryLabel, MaterialTheme.shapes.medium)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        importanceOptions.forEach { option ->
            val icon = importanceIcons[option]
            val color = importanceColors[option] ?: Color.Black
            val isSelected = option == selectedImportance

            Box(
                modifier = Modifier
                    .background(
                        if (isSelected) LocalExtendedColors.current.supportOverlay else Color(0xFFF5F5F5),
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable { onImportanceSelected(option) }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (icon != null) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = "Важность",
                            tint = color,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}