package com.tisenres.yandextodoapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListScreen
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListViewModel
import com.tisenres.yandextodoapp.presentation.theme.YandexTodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YandexTodoAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "tasks") {
                    composable("taskList") {
                        val viewModel = hiltViewModel<TodoListViewModel>()
                        TodoListScreen(
                            viewModel,
                            onTodoClick = { taskId ->
                                navController.navigate("taskDetails/$taskId")
                            },
                            onCreateTaskClick = {
                                navController.navigate("createTask")
                            }
                        )
                    }
                    composable("taskDetails/{taskId}") {

                    }
                }
            }
        }
    }
}