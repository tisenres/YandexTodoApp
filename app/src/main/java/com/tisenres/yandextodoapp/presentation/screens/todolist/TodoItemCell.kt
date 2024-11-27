package com.tisenres.yandextodoapp.presentation.screens.todolist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tisenres.yandextodoapp.R
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.presentation.theme.LocalExtendedColors

@Composable
fun TodoItemCell(
    text: String,
    importance: Importance,
    isCompleted: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onItemClick: (String) -> Unit,
) {
    val checkboxColor = when {
        isCompleted -> LocalExtendedColors.current.green
        importance == Importance.HIGH -> LocalExtendedColors.current.red
        else -> LocalExtendedColors.current.supportSeparator
    }
    val textColor = if (isCompleted) {
        LocalExtendedColors.current.tertiaryLabel
    } else {
        LocalExtendedColors.current.primaryLabel
    }
    val textDecoration = if (isCompleted) TextDecoration.LineThrough else null

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(text) })
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isCompleted,
            onCheckedChange = { checked ->
                onCheckedChange(checked)
            },
            modifier = Modifier
                .padding(end = 12.dp),
            colors = CheckboxDefaults.colors(
                checkedColor = checkboxColor,
                uncheckedColor = checkboxColor,
                checkmarkColor = Color.White
            )
        )

        Row(
            modifier = Modifier
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when (importance) {
                Importance.HIGH -> {
                    Icon(
                        painter = painterResource(R.drawable.priority_high),
                        contentDescription = "High Priority",
                        tint = LocalExtendedColors.current.red,
                        modifier = Modifier.padding(end = 7.dp)
                    )
                }

                Importance.LOW -> {
                    Icon(
                        painter = painterResource(R.drawable.priority_low),
                        contentDescription = "Low Priority",
                        tint = LocalExtendedColors.current.gray,
                        modifier = Modifier.padding(end = 7.dp)
                    )
                }

                else -> {}
            }

            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                textDecoration = textDecoration,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 7.dp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.info_outline),
            contentDescription = "More Details",
            tint = LocalExtendedColors.current.tertiaryLabel,
        )
    }
}