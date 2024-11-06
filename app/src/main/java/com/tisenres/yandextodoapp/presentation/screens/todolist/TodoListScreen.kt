package com.tisenres.yandextodoapp.presentation.screens.todolist

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    onCreateTaskClick: () -> Unit
) {
    val todos = viewModel.todos.collectAsState()

    TodoListContent(
        tasks = todos.value,
        onTodoClick = onTodoClick,
        onCreateTaskClick = onCreateTaskClick,
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun TodoListContent(
    tasks: List<TodoItem>,
    onTodoClick: (String) -> Unit,
    onCreateTaskClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateTaskClick,
                containerColor = LocalExtendedColors.current.blue,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .padding(bottom = 24.dp, end = 16.dp)
                    .clip(CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "Add Task",
                    tint = Color.White
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .background(LocalExtendedColors.current.primaryBackground)
        ) {
            HeaderAndCompletedTasks(
                completedTodos = tasks.count { it.isCompleted },
                onEyeClick = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            TodoList(
                todos = tasks,
                onTodoClick = onTodoClick
            )
        }
    }
}

@Composable
fun HeaderAndCompletedTasks(
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
                contentDescription = if (isVisible) "Hide Completed Tasks" else "Show Completed Tasks",
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
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        tonalElevation = 2.dp
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(LocalExtendedColors.current.secondaryBackground)
        ) {
            items(todos) { item ->
                TodoItemCell(
                    text = item.text,
                    importance = item.importance,
                    isCompleted = item.isCompleted,
                    onClick = { onTodoClick(item.id) },
                    onCheckedChange = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun TodoListScreenPreview() {
    // Provide your preview here
}