package com.tisenres.yandextodoapp.presentation.screens.todolist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tisenres.yandextodoapp.R
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.presentation.theme.LocalExtendedColors

@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel,
    onTodoItemClick: (String) -> Unit,
    onCreateTodoClick: () -> Unit
) {
    val todos by viewModel.todos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isError by viewModel.isError.collectAsState()

    val onShowCompletedTasks by viewModel.onShowCompletedTasks.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    rememberCoroutineScope()

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
            viewModel.clearErrorMessage()
        }
    }

    TodoListContent(
        todos = todos,
        onTodoItemClick = onTodoItemClick,
        onCreateTodoClick = onCreateTodoClick,
        onCompleteTodo = { todoId -> viewModel.completeTodo(todoId) },
        onDeleteTodo = { todoId -> viewModel.deleteTodo(todoId) },
        isLoading = isLoading,
        isError = isError,
        onRetryClick = { viewModel.refreshTodosWithRetry() },
        snackbarHostState = snackbarHostState,
        modifier = Modifier.fillMaxSize(),
        onShowCompletedTasks = onShowCompletedTasks,
        viewModel = viewModel
    )
}

@Composable
fun TodoListContent(
    todos: List<TodoItem>,
    onTodoItemClick: (String) -> Unit,
    onCreateTodoClick: () -> Unit,
    onCompleteTodo: (String) -> Unit,
    onDeleteTodo: (String) -> Unit,
    onShowCompletedTasks: Boolean,
    isLoading: Boolean,
    isError: Boolean,
    onRetryClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel
) {

    val filteredTodos = remember(onShowCompletedTasks, todos) {
        if (onShowCompletedTasks) {
            todos
        } else {
            todos.filter { !it.isCompleted }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateTodoClick,
                containerColor = LocalExtendedColors.current.blue,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 16.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = stringResource(R.string.add_todo),
                    tint = Color.White
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
                    Snackbar(
                        snackbarData = snackbarData,
                        containerColor = LocalExtendedColors.current.elevatedBackground,
                        contentColor = LocalExtendedColors.current.red,
                    )
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(LocalExtendedColors.current.primaryBackground)
                .padding(paddingValues)
        ) {
            if (isError && todos.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.error_loading_data),
                        style = MaterialTheme.typography.bodyLarge,
                        color = LocalExtendedColors.current.primaryLabel
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onRetryClick) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    HeaderAndCompletedTodos(
                        completedTodos = todos.count { it.isCompleted },
                        onEyeClick = { viewModel.toggleEyeButton() }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TodoList(
                        todos = filteredTodos,
                        onTodoItemClick = onTodoItemClick,
                        onCompleteTodo = onCompleteTodo,
                        onCreateTodoClick = onCreateTodoClick,
                        onDeleteTodo = onDeleteTodo,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        isLoading = isLoading,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderAndCompletedTodos(
    completedTodos: Int,
    onEyeClick: () -> Unit,
) {
    var isVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 60.dp, top = 50.dp, end = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.my_todos),
            style = MaterialTheme.typography.titleLarge,
            color = LocalExtendedColors.current.primaryLabel
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.completed) + " — $completedTodos",
                style = MaterialTheme.typography.bodySmall,
                color = LocalExtendedColors.current.tertiaryLabel,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(if (!isVisible) R.drawable.visibility else R.drawable.visibility_off),
                tint = LocalExtendedColors.current.blue,
                contentDescription = if (!isVisible) "Hide Completed Todos" else "Show Completed Todos",
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable {
                        isVisible = !isVisible
                        onEyeClick()
                    }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(
    todos: List<TodoItem>,
    onTodoItemClick: (String) -> Unit,
    onCompleteTodo: (String) -> Unit,
    onCreateTodoClick: () -> Unit,
    onDeleteTodo: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    viewModel: TodoListViewModel,
) {

    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullRefreshState,
        onRefresh = {
            if (!isLoading) viewModel.refreshTodosWithRetry()
        },
        isRefreshing = isLoading,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullRefreshState,
                isRefreshing = isLoading,
                color = LocalExtendedColors.current.green,
                containerColor = Color.White,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(LocalExtendedColors.current.secondaryBackground)
        ) {
            items(
                items = todos,
                key = { it.id }
            ) { item ->
                val swipeState = rememberSwipeToDismissBoxState()

                SwipeToDismissBox(
                    state = swipeState,
                    modifier = Modifier.animateContentSize(),
                    backgroundContent = {
                        val dismissDirection = swipeState.dismissDirection
                        val (icon, alignment, color) = when (dismissDirection) {
                            SwipeToDismissBoxValue.EndToStart -> {
                                Triple(
                                    Icons.Filled.Delete,
                                    Alignment.CenterEnd,
                                    LocalExtendedColors.current.error
                                )
                            }

                            SwipeToDismissBoxValue.StartToEnd -> {
                                Triple(
                                    Icons.Filled.Done,
                                    Alignment.CenterStart,
                                    LocalExtendedColors.current.green
                                )
                            }

                            else -> {
                                Triple(null, Alignment.Center, Color.Transparent)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            if (icon != null) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    content = {
                        TodoItemCell(
                            text = item.text,
                            importance = item.importance,
                            isCompleted = item.isCompleted,
                            onItemClick = { onTodoItemClick(item.id) },
                            onCheckedChange = {
                                onCompleteTodo(item.id)
                            },
                        )
                    }
                )

                when (swipeState.currentValue) {
                    SwipeToDismissBoxValue.EndToStart -> {
                        LaunchedEffect(Unit) {
                            onDeleteTodo(item.id)
                            swipeState.snapTo(SwipeToDismissBoxValue.Settled)
                        }
                    }

                    SwipeToDismissBoxValue.StartToEnd -> {
                        LaunchedEffect(Unit) {
                            onCompleteTodo(item.id)
                            swipeState.snapTo(SwipeToDismissBoxValue.Settled)
                        }
                    }

                    else -> {

                    }
                }
            }

            item {
                newTaskRow(onCreateTodoClick = onCreateTodoClick)
            }
        }
    }
}

@Composable
fun newTaskRow(onCreateTodoClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCreateTodoClick() }
            .padding(horizontal = 80.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.new_todo),
            style = MaterialTheme.typography.bodyLarge,
            color = LocalExtendedColors.current.tertiaryLabel,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}