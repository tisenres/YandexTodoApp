package com.tisenres.yandextodoapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListScreen
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsScreen
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsViewModel
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
                NavHost(navController = navController, startDestination = "taskList") {
                    composable("taskList") {
                        TodoListScreen(
                            onTodoClick = { taskId ->
                                navController.navigate("taskDetails/$taskId")
                            },
                            onCreateTaskClick = {
                                navController.navigate("createTask")
                            }
                        )
                    }
                    composable(
                        "taskDetails/{taskId}",
                        arguments = listOf(navArgument("taskId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getString("taskId") ?: return@composable
                        val viewModel = hiltViewModel<TodoDetailsViewModel>()
                        viewModel.loadTask(taskId)

                        val taskState by viewModel.task.collectAsState()

                        TodoDetailsScreen(
                            initialText = taskState?.text ?: "",
                            initialImportance = taskState?.importance ?: Importance.NORMAL,
                            initialDeadline = taskState?.deadline,
                            isEditing = true,
                            onSaveClick = { text, importance, deadline ->
                                viewModel.updateTask(taskId, text, importance, deadline)
                                navController.popBackStack()
                            },
                            onDeleteClick = {
                                viewModel.deleteTask(taskId)
                                navController.popBackStack()
                            },
                            onCloseClick = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable("createTask") {
                        val viewModel = hiltViewModel<TodoDetailsViewModel>()

                        TodoDetailsScreen(
                            onSaveClick = { text, importance, deadline ->
                                viewModel.createTask(text, importance, deadline)
                                navController.popBackStack()
                            },
                            onDeleteClick = {
                                navController.popBackStack()
                            },
                            onCloseClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}