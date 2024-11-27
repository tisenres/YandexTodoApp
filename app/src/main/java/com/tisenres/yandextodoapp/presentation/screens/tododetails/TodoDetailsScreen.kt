package com.tisenres.yandextodoapp.presentation.screens.tododetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun TodoDetailsScreen(
    todoId: String,
    isEditing: Boolean = false,
    onSaveClick: (String, Importance, Date?) -> Unit,
    onDeleteClick: () -> Unit,
    onCloseClick: () -> Unit,
    viewModel: TodoDetailsViewModel
) {
    val todoItem by viewModel.todo.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var text by remember { mutableStateOf(TextFieldValue("")) }
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

    LaunchedEffect(todoItem) {
        todoItem?.let {
            text = TextFieldValue(it.text)
            importance = it.importance
            deadline = it.deadline
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
            TodoDetailsTopBar(
                text = text.text,
                onCloseClick = onCloseClick,
                onSaveClick = {
                    if (text.text.isNotBlank()) {
                        onSaveClick(text.text, importance, deadline)
                    }
                }
            )
        },
        containerColor = LocalExtendedColors.current.primaryBackground,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->

            if (isLoading) {
                LoadingContent()
            } else {
                TodoDetailsContent(
                    paddingValues = paddingValues,
                    text = text,
                    onTextChanged = { text = it },
                    importance = importance,
                    onImportanceChanged = { importance = it },
                    deadline = deadline,
                    onDeadlineChanged = { deadline = it },
                    isDatePickerVisible = isDatePickerVisible,
                    onDatePickerVisibilityChanged = { isDatePickerVisible = it },
                    dateFormat = dateFormat,
                    isEditing = isEditing,
                    onDeleteClick = onDeleteClick
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailsTopBar(
    text: String,
    onCloseClick: () -> Unit,
    onSaveClick: () -> Unit
) {
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
                onClick = onSaveClick,
                enabled = text.isNotBlank()
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (text.isNotBlank()) {
                        LocalExtendedColors.current.blue
                    } else {
                        LocalExtendedColors.current.disableLabel
                    }
                )
            }
        },
    )
}

@Composable
fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalExtendedColors.current.primaryBackground)
    ) {
        CircularProgressIndicator(
            color = LocalExtendedColors.current.green,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TodoDetailsContent(
    paddingValues: PaddingValues,
    text: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    importance: Importance,
    onImportanceChanged: (Importance) -> Unit,
    deadline: Date?,
    onDeadlineChanged: (Date?) -> Unit,
    isDatePickerVisible: Boolean,
    onDatePickerVisibilityChanged: (Boolean) -> Unit,
    dateFormat: SimpleDateFormat,
    isEditing: Boolean,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        TodoDetailsTextField(
            text = text,
            onTextChanged = onTextChanged
        )

        ImportanceRow(
            importance = importance,
            onImportanceChanged = onImportanceChanged
        )

        HorizontalDivider()

        DeadlineRow(
            deadline = deadline,
            onDeadlineChanged = onDeadlineChanged,
            isDatePickerVisible = isDatePickerVisible,
            onDatePickerVisibilityChanged = onDatePickerVisibilityChanged,
            dateFormat = dateFormat
        )

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))

        if (isEditing) {
            DeleteButton(onDeleteClick = onDeleteClick)
        }
    }
}

@Composable
fun TodoDetailsTextField(
    text: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit
) {
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
                onValueChange = onTextChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(104.dp)
                    .padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 16.dp,
                        bottom = 12.dp
                    ),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
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
        }
    }
}

@Composable
fun ImportanceRow(
    importance: Importance,
    onImportanceChanged: (Importance) -> Unit
) {
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
            onImportanceSelected = onImportanceChanged
        )
    }
}

@Composable
fun DeadlineRow(
    deadline: Date?,
    onDeadlineChanged: (Date?) -> Unit,
    isDatePickerVisible: Boolean,
    onDatePickerVisibilityChanged: (Boolean) -> Unit,
    dateFormat: SimpleDateFormat
) {
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
                    onDatePickerVisibilityChanged(true)
                } else {
                    onDeadlineChanged(null)
                }
            }
        )
    }

    if (isDatePickerVisible) {
        CustomDatePickerDialog(
            initialDate = deadline ?: Date(),
            onDateSelected = { date ->
                onDeadlineChanged(date)
                onDatePickerVisibilityChanged(false)
            },
            onDismissRequest = {
                onDatePickerVisibilityChanged(false)
            }
        )
    }
}

@Composable
fun DeleteButton(
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
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
                    .size(24.dp),
                tint = LocalExtendedColors.current.red,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(R.string.delete),
                style = MaterialTheme.typography.bodyLarge,
                color = LocalExtendedColors.current.red
            )
        }
    }
}