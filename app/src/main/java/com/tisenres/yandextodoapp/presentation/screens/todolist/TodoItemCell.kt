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
    onClick: () -> Unit
) {
    val checkboxColor = when {
        isCompleted -> Color.Green
        importance == Importance.HIGH -> Color.Red
        else -> Color.Gray
    }
    val textColor = if (isCompleted) LocalExtendedColors.current.supportSeparator else LocalExtendedColors.current.primaryLabel
    val textDecoration = if (isCompleted) TextDecoration.LineThrough else null

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        if (isCompleted) {
            Checkbox(
                checked = true,
                onCheckedChange = null,
                modifier = Modifier.padding(end = 12.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = LocalExtendedColors.current.green,
                    checkmarkColor = Color.White
                )
            )
        } else {
            when (importance) {
                Importance.HIGH -> {
                    Checkbox(
                        checked = false,
                        onCheckedChange = null,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .align(Alignment.CenterVertically),
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = checkboxColor
                        )
                    )
                    Icon(
                        painter = painterResource(R.drawable.priority_high),
                        contentDescription = "High Priority",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .align(Alignment.CenterVertically)
                    )
                }
                Importance.LOW -> {
                    Checkbox(
                        checked = false,
                        onCheckedChange = null,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .align(Alignment.CenterVertically),
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = checkboxColor
                        )
                    )
                    Icon(
                        painter = painterResource(R.drawable.priority_low),
                        contentDescription = "Low Priority",
                        tint = Color.Gray,
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .align(Alignment.CenterVertically)

                    )
                }
                else -> {
                    Checkbox(
                        checked = false,
                        onCheckedChange = null,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .align(Alignment.CenterVertically),
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = checkboxColor
                        )
                    )
                }
            }
        }

        // Текст задачи
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = textColor,
                textDecoration = textDecoration
            ),
            modifier = Modifier.weight(1f),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        // Иконка "Подробнее"
        Icon(
            painter = painterResource(R.drawable.info_outline),
            contentDescription = "Подробнее о задаче",
            tint = LocalExtendedColors.current.tertiaryLabel,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemCellPreview() {
    Column {
        TodoItemCell(
            text = "Завершенная задача",
            importance = Importance.NORMAL,
            isCompleted = true,
            onClick = {}
        )
        TodoItemCell(
            text = "Задача с высоким приоритетом",
            importance = Importance.HIGH,
            isCompleted = false,
            onClick = {}
        )
        TodoItemCell(
            text = "Задача с обычным приоритетом",
            importance = Importance.NORMAL,
            isCompleted = false,
            onClick = {}
        )
        TodoItemCell(
            text = "Задача с низким приоритетом",
            importance = Importance.LOW,
            isCompleted = false,
            onClick = {}
        )
    }
}
