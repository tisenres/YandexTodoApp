package com.tisenres.yandextodoapp.presentation.screens.todolist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tisenres.yandextodoapp.R
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.presentation.theme.LocalExtendedColors
import java.util.Date

@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel = hiltViewModel(),
    onTodoClick: (String, String) -> Unit,
    onCreateTodoClick: () -> Unit
) {
    val todos by viewModel.todos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
            viewModel.clearErrorMessage()
        }
    }

    TodoListContent(
        todos = todos,
        onTodoClick = onTodoClick,
        onCreateTodoClick = onCreateTodoClick,
        onCompleteTodo = { todoId -> viewModel.completeTodo(todoId) },
        onDeleteTodo = { todoId -> viewModel.deleteTodo(todoId) },
        isLoading = isLoading,
        snackbarHostState = snackbarHostState,
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel
    )
}

@Composable
fun TodoListContent(
    todos: List<TodoItem>,
    onTodoClick: (String, String) -> Unit,
    onCreateTodoClick: () -> Unit,
    onCompleteTodo: (String) -> Unit,
    onDeleteTodo: (String) -> Unit,
    isLoading: Boolean,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel
) {
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
                    contentDescription = "Add Todo",
                    tint = Color.White
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp),
                snackbar = { snackbarData ->
                    Snackbar(
                        snackbarData = snackbarData,
                        containerColor = LocalExtendedColors.current.elevatedBackground,
                        contentColor = LocalExtendedColors.current.red,
                    )
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LocalExtendedColors.current.primaryBackground)
        ) {
            HeaderAndCompletedTodos(
                completedTodos = todos.count { it.isCompleted },
                onEyeClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            TodoList(
                todos = todos,
                onTodoClick = onTodoClick,
                onCompleteTodo = onCompleteTodo,
                onCreateTodoClick = onCreateTodoClick,
                onDeleteTodo = onDeleteTodo,
                modifier = Modifier.weight(1f),
                isLoading = isLoading,
                viewModel = viewModel
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .background(Color.Black.copy(alpha = 0.5f))
            )
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
            text = "Мои дела",
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
                text = "Выполнено — $completedTodos",
                style = MaterialTheme.typography.bodySmall,
                color = LocalExtendedColors.current.tertiaryLabel,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(if (isVisible) R.drawable.visibility else R.drawable.visibility_off),
                tint = LocalExtendedColors.current.blue,
                contentDescription = if (isVisible) "Hide Completed Todos" else "Show Completed Todos",
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
    onTodoClick: (String, String) -> Unit,
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
        onRefresh = { viewModel.refreshTodos() },
        isRefreshing = isLoading,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullRefreshState,
                isRefreshing = isLoading,
                color = Color(0xFF32B768),
                containerColor = Color.White,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {


        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = modifier
                .fillMaxWidth()
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
                            onClick = { text -> onTodoClick(item.id, text) },
                            onCheckedChange = {}
                        )
                    }
                )

                when (swipeState.currentValue) {
                    SwipeToDismissBoxValue.EndToStart -> {
                        LaunchedEffect(Unit) {
                            onDeleteTodo(item.id)
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
            text = "Новое",
            style = MaterialTheme.typography.bodyLarge,
            color = LocalExtendedColors.current.tertiaryLabel,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderAndCompletedTodosPreview() {
    HeaderAndCompletedTodos(
        completedTodos = 5,
        onEyeClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun NewTaskRowPreview() {
    newTaskRow(onCreateTodoClick = {})
}

@Preview(showBackground = true)
@Composable
fun TodoListPreview() {
    val sampleTodos = listOf(
        TodoItem(
            id = "1",
            text = "Задача 1",
            importance = Importance.NORMAL,
            isCompleted = false,
            createdAt = Date()
        ),
        TodoItem(
            id = "2",
            text = "Задача 2",
            importance = Importance.HIGH,
            isCompleted = true,
            createdAt = Date()
        ),
        TodoItem(
            id = "3",
            text = "Задача 3",
            importance = Importance.LOW,
            isCompleted = false,
            createdAt = Date()
        )
    )
    TodoList(
        todos = sampleTodos,
        onTodoClick = { _, _ -> },
        onCompleteTodo = {},
        onCreateTodoClick = {},
        onDeleteTodo = {},
        modifier = Modifier,
        isLoading = false,
        viewModel = hiltViewModel()
    )
}

@Preview(showBackground = true)
@Composable
fun TodoListContentPreview() {
    val sampleTodos = listOf(
        TodoItem(
            id = "1",
            text = "Задача 1",
            importance = Importance.NORMAL,
            isCompleted = false,
            createdAt = Date()
        ),
        TodoItem(
            id = "2",
            text = "Задача 2",
            importance = Importance.HIGH,
            isCompleted = true,
            createdAt = Date()
        ),
        TodoItem(
            id = "3",
            text = "Задача 3",
            importance = Importance.LOW,
            isCompleted = false,
            createdAt = Date()
        )
    )
//    TodoListContent(
//        todos = sampleTodos,
//        onTodoClick = { _, _ -> },
//        onCreateTodoClick = {},
//        onCompleteTodo = {},
//        onDeleteTodo = {},
//        isLoading = true,
//        viewModel = hiltViewModel()
//    )
}