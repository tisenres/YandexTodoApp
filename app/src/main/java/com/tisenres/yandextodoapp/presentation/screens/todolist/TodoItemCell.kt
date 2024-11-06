package com.tisenres.yandextodoapp.presentation.screens.todolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Checkbox(
            checked = isCompleted,
            onCheckedChange = null, // Checkbox can be read-only if needed
            modifier = Modifier.padding(end = 12.dp),
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color.Gray,
                checkedColor = MaterialTheme.colorScheme.primary
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = LocalExtendedColors.current.primaryLabel,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(R.drawable.info_outline),
            contentDescription = "Task Details",
            tint = LocalExtendedColors.current.tertiaryLabel,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TodoItemCellPreview() {
    TodoItemCell(
        text = "Hello",
        importance = Importance.HIGH,
        isCompleted = false,
        {}
    )
}
