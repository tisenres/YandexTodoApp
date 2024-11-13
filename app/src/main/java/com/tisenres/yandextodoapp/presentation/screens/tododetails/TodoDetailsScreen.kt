package com.tisenres.yandextodoapp.presentation.screens.tododetails

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
    todoId: String,
    initialText: String = "",
    isEditing: Boolean = false,
    onSaveClick: (String, Importance, Date?) -> Unit,
    onDeleteClick: () -> Unit,
    onCloseClick: () -> Unit,
    viewModel: TodoDetailsViewModel
) {
    val todoItem by viewModel.todo.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var text by remember { mutableStateOf(TextFieldValue(initialText)) }
    var importance by remember { mutableStateOf(Importance.NORMAL) }
    var deadline by remember { mutableStateOf<Date?>(null) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(todoId) {
        if (todoId.isNotEmpty()) {
            viewModel.getTodoById(todoId)
        }
    }

    // Update importance and deadline when todoItem is loaded
    LaunchedEffect(todoItem) {
        if (todoItem != null) {
            importance = todoItem!!.importance
            deadline = todoItem!!.deadline
            // Do NOT update text here, as it's passed via navigation
            // text = TextFieldValue(todoItem.text)
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
            viewModel.clearErrorMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onCloseClick) {
                        Icon(
                            painterResource(R.drawable.close),
                            contentDescription = stringResource(R.string.close)
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (text.text.isNotBlank()) {
                                onSaveClick(text.text, importance, deadline)
                            }
                        },
                        enabled = text.text.isNotBlank()
                    ) {
                        Text(
                            text = stringResource(R.string.save),
                            style = MaterialTheme.typography.labelSmall,
                            color = if (text.text.isNotBlank()) LocalExtendedColors.current.blue else LocalExtendedColors.current.disableLabel
                        )
                    }
                },
            )
        },
        containerColor = LocalExtendedColors.current.primaryBackground,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                // Text input field with validation
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = LocalExtendedColors.current.elevatedBackground,
                    shadowElevation = 4.dp
                ) {
                    Column {
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
                                            stringResource(R.string.what_to_do),
                                            color = LocalExtendedColors.current.tertiaryLabel
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
//                        if (text.text.isEmpty()) {
//                            Text(
//                                text = stringResource(R.string.error_empty_field),
//                                color = MaterialTheme.colorScheme.error,
//                                style = MaterialTheme.typography.bodySmall,
//                                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
//                            )
//                        }
                    }
                }

                // Importance selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 26.5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.importance),
                        style = MaterialTheme.typography.bodyLarge,
                        color = LocalExtendedColors.current.primaryLabel,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ImportanceSelector(
                        selectedImportance = importance,
                        onImportanceSelected = { importance = it }
                    )
                }

                HorizontalDivider()

                // Deadline selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 26.5.dp)
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.do_until),
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

                // Show date picker dialog if needed
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
                                contentColor = LocalExtendedColors.current.red
                            ),
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.delete),
                                contentDescription = stringResource(R.string.delete),
                                modifier = Modifier
                                    .size(24.dp)
                                    .align(Alignment.CenterVertically),
                                tint = LocalExtendedColors.current.red,
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = stringResource(R.string.delete),
                                style = MaterialTheme.typography.bodyLarge,
                                color = LocalExtendedColors.current.red,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }
    )
}