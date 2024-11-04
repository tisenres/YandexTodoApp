package com.tisenres.yandextodoapp.presentation.screens.todolist

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TaskListScreen(
    viewModel: TodoListViewModel = hiltViewModel(),
    onTaskClick: (Long) -> Unit,
    onCreateTaskClick: () -> Unit
) {
    TaskListContent(
        tasks = viewModel.tasks,
        onTaskClick = onTaskClick,
        onCreateTaskClick = onCreateTaskClick
    )
}

@Composable
fun TaskListContent(
    tasks: Any,
    onTaskClick: (Long) -> Unit,
    onCreateTaskClick: () -> Unit
) {


}
