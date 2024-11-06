package com.tisenres.yandextodoapp.presentation.screens.todolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
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
    onClick: () -> Unit
) {
    val checkboxColor = when {
        isCompleted -> LocalExtendedColors.current.green
        importance == Importance.HIGH -> LocalExtendedColors.current.red
        else -> LocalExtendedColors.current.supportSeparator
    }
    val textColor = if (isCompleted) LocalExtendedColors.current.tertiaryLabel else LocalExtendedColors.current.primaryLabel
    val textDecoration = if (isCompleted) TextDecoration.LineThrough else null

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = isCompleted,
            onCheckedChange = onCheckedChange,
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
                        tint = Color.Red,
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
                overflow = TextOverflow.Ellipsis
            )
        }

        Icon(
            painter = painterResource(R.drawable.info_outline),
            contentDescription = "More Details",
            tint = LocalExtendedColors.current.tertiaryLabel,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemCellPreview() {
    Column {
        // Завершенная задача
        TodoItemCell(
            text = "Завершенная задача",
            importance = Importance.NORMAL,
            isCompleted = true,
            onCheckedChange = {},
            onClick = {}
        )
        // Задача с высоким приоритетом
        TodoItemCell(
            text = "Срочная задача с высоким приоритетом",
            importance = Importance.HIGH,
            isCompleted = false,
            onCheckedChange = {},
            onClick = {}
        )
        // Задача с обычным приоритетом
        TodoItemCell(
            text = "Обычная задача",
            importance = Importance.NORMAL,
            isCompleted = false,
            onCheckedChange = {},
            onClick = {}
        )
        // Задача с низким приоритетом
        TodoItemCell(
            text = "Задача с низким приоритетом",
            importance = Importance.LOW,
            isCompleted = false,
            onCheckedChange = {},
            onClick = {}
        )
        // Длинный текст задачи
        TodoItemCell(
            text = "Это очень длинный текст задачи, который должен быть ограничен тремя строками. Мы продолжаем писать, чтобы убедиться, что текст действительно обрезается после третьей строки и отображается многоточие в конце.",
            importance = Importance.NORMAL,
            isCompleted = false,
            onCheckedChange = {},
            onClick = {}
        )
    }
}