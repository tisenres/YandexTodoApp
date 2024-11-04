package com.tisenres.yandextodoapp.presentation.screens.todolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tisenres.yandextodoapp.R
import com.tisenres.yandextodoapp.domain.entity.Importance

@Composable
fun TodoItemCell(
    text: String,
    importance: Importance,
    isCompleted: Boolean,
) {

    val importanceColor = when (importance) {
        Importance.Low -> Color.Green
        Importance.Normal -> Color.Yellow
        Importance.High -> Color.Red
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isCompleted,
            onCheckedChange = {
                // Handle checkbox state change
            }
        )

        Text(
            modifier = Modifier.weight(1f),
            text = text,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Icon(
            painter = painterResource(R.drawable.info_outline),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary
        )

    }
}

@Preview
@Composable
fun TodoItemCellPreview() {
    TodoItemCell(
        text = "Task 1",
        importance = Importance.Low,
        isCompleted = false
    )
}