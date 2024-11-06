package com.tisenres.yandextodoapp.presentation.screens.tododetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.tisenres.yandextodoapp.R
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.presentation.screens.tododetails.components.CustomDatePickerDialog
import com.tisenres.yandextodoapp.presentation.screens.tododetails.components.ImportanceSelector
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailsScreen(
    initialText: String = "",
    initialImportance: Importance = Importance.NORMAL,
    initialDeadline: Date? = null,
    isEditing: Boolean = false,
    onSaveClick: (String, Importance, Date?) -> Unit,
    onDeleteClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue(initialText)) }
    var importance by remember { mutableStateOf(initialImportance) }
    var deadline by remember { mutableStateOf(initialDeadline) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onCloseClick) {
                        Icon(painterResource(R.drawable.close), contentDescription = "Close")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        onSaveClick(text.text, importance, deadline)
                    }) {
                        Text("СОХРАНИТЬ", color = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(100.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.fillMaxSize()) {
                            if (text.text.isEmpty()) {
                                Text(
                                    "Что надо сделать...",
                                    color = Color.Gray
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Text("Важность", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                ImportanceSelector(
                    selectedImportance = importance,
                    onImportanceSelected = { importance = it }
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Сделать до", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = deadline != null,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                isDatePickerVisible = true
                            } else {
                                deadline = null
                            }
                        }
                    )
                }
                if (deadline != null) {
                    Text(
                        text = dateFormat.format(deadline),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                if (isDatePickerVisible) {
                    CustomDatePickerDialog(
                        initialDate = deadline ?: Date(),
                        onDateSelected = { date ->
                            deadline = date
                            isDatePickerVisible = false
                        },
                        onDismissRequest = {
                            isDatePickerVisible = false
                        }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (isEditing) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = onDeleteClick,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Red
                            )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.delete),
                                contentDescription = "Удалить"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Удалить")
                        }
                    }
                }
            }
        }
    )
}