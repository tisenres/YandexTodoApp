package com.tisenres.yandextodoapp.presentation.screens.todolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        modifier = Modifier.fillMaxSize()
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
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "Add Task",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            HeaderAndCompletedTasks(
                completedTodos = tasks.count { it.isCompleted },
                onEyeClick = {}
            )

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

    val extendedColors = LocalExtendedColors.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 60.dp, top = 50.dp, end = 24.dp) // Adjust padding as needed
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
                painter = painterResource(R.drawable.visibility),
                tint = LocalExtendedColors.current.blue, // Use customBlue here
                contentDescription = "Show Completed Tasks",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(onClick = onEyeClick)
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
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface),
        tonalElevation = 2.dp
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(todos) { item ->
                TodoItemCell(
                    text = item.text,
                    importance = item.importance,
                    isCompleted = item.isCompleted,
                    onClick = { onTodoClick(item.id) }
                )
            }
        }
    }
}

@Preview
@Composable
fun TodoListScreenPreview() {
}

