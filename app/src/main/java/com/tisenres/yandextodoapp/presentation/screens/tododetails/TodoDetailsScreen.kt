package com.tisenres.yandextodoapp.presentation.screens.tododetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.tisenres.yandextodoapp.R
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.presentation.screens.tododetails.components.CustomDatePickerDialog
import com.tisenres.yandextodoapp.presentation.screens.tododetails.components.ImportanceSelector
import com.tisenres.yandextodoapp.presentation.theme.LocalExtendedColors
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
                        Text(
                            text = "СОХРАНИТЬ",
                            style = MaterialTheme.typography.labelSmall,
                            color = LocalExtendedColors.current.blue
                        )
                    }
                },
            )
        },
        containerColor = LocalExtendedColors.current.primaryBackground,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = LocalExtendedColors.current.secondaryBackground,
                    shadowElevation = 4.dp
                ) {
                    BasicTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(104.dp)
                            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 12.dp),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                        decorationBox = { innerTextField ->
                            Box {
                                if (text.text.isEmpty()) {
                                    Text(
                                        "Что надо сделать...",
                                        color = LocalExtendedColors.current.tertiaryLabel
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 26.5.dp)
                ) {
                    Text(
                        text = "Важность",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LocalExtendedColors.current.primaryLabel,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ImportanceSelector(
                        selectedImportance = importance,
                        onImportanceSelected = { importance = it }
                    )
                }

                HorizontalDivider()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 26.5.dp)
                ) {
                    Column {
                        Text(
                            text = "Сделать до",
                            style = MaterialTheme.typography.bodyLarge,
                            color = LocalExtendedColors.current.primaryLabel,
                        )
                        if (deadline != null) {
                            Text(
                                text = dateFormat.format(deadline),
                                color = LocalExtendedColors.current.blue,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
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

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider()

                Spacer(modifier = Modifier.height(8.dp))

                if (isEditing) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        TextButton(
                            onClick = onDeleteClick,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Red
                            )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.delete),
                                contentDescription = "Удалить",
                                modifier = Modifier.size(24.dp),
                                tint = Color.Red
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Удалить",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    )
}