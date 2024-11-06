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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tisenres.yandextodoapp.R
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.presentation.theme.LocalExtendedColors

@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel = hiltViewModel(),
    onTodoClick: (String) -> Unit,
    onCreateTodoClick: () -> Unit
) {
    val todos by viewModel.todos.collectAsState()
F
    TodoListContent(
        todos = todos,
        onTodoClick = onTodoClick,
        onCreateTodoClick = onCreateTodoClick,
        onCompleteTodo = { todoId -> viewModel.completeTodo(todoId) },
        onDeleteTodo = { todoId -> viewModel.deleteTodo(todoId) },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun TodoListContent(
    todos: List<TodoItem>,
    onTodoClick: (String) -> Unit,
    onCreateTodoClick: () -> Unit,
    onCompleteTodo: (String) -> Unit,
    onDeleteTodo: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateTodoClick,
                containerColor = LocalExtendedColors.current.blue,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "Add Todo",
                    tint = Color.White
                )
            }
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
                onDeleteTodo = onDeleteTodo,
                modifier = Modifier.weight(1f),
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

@Composable
fun TodoList(
    todos: List<TodoItem>,
    onTodoClick: (String) -> Unit,
    onCompleteTodo: (String) -> Unit,
    onDeleteTodo: (String) -> Unit,
    modifier: Modifier = Modifier,
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
                        onClick = { onTodoClick(item.id) },
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
    }
}

@Preview
@Composable
fun TodoListScreenPreview() {

}
