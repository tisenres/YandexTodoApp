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
                NavHost(navController = navController, startDestination = "todoList") {
                    composable("todoList") {
                        TodoListScreen(
                            onTodoClick = { todoId, todoText ->
                                navController.navigate("todoDetails/$todoId/$todoText")
                            },
                            onCreateTodoClick = {
                                navController.navigate("createTodo")
                            }
                        )
                    }
                    composable(
                        "todoDetails/{todoId}/{todoText}",
                        arguments = listOf(
                            navArgument("todoId") { type = NavType.StringType },
                            navArgument("todoText") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val todoId =
                            backStackEntry.arguments?.getString("todoId") ?: return@composable
                        val todoText =
                            backStackEntry.arguments?.getString("todoText") ?: return@composable
                        val viewModel = hiltViewModel<TodoDetailsViewModel>()
                        viewModel.getTodoById(todoId)

                        val todoState by viewModel.todo.collectAsState()

                        TodoDetailsScreen(
                            todoId = todoId,
                            initialText = todoText,
                            initialImportance = todoState?.importance ?: Importance.NORMAL,
                            initialDeadline = todoState?.deadline,
                            isEditing = true,
                            onSaveClick = { text, importance, deadline ->
                                viewModel.updateTodo(todoId, text, importance, deadline)
                                navController.popBackStack()
                            },
                            onDeleteClick = {
                                viewModel.deleteTodo(todoId)
                                navController.popBackStack()
                            },
                            onCloseClick = {
                                navController.popBackStack()
                            },
                            viewModel = hiltViewModel()
                        )
                    }

                    composable("createTodo") {
                        val viewModel = hiltViewModel<TodoDetailsViewModel>()

                        TodoDetailsScreen(
                            todoId = "",
                            initialText = "",
                            initialImportance = Importance.NORMAL,
                            initialDeadline = null,
                            isEditing = false,
                            onSaveClick = { text, importance, deadline ->
                                viewModel.createTodo(text, importance, deadline)
                                navController.popBackStack()
                            },
                            onDeleteClick = {
                                navController.popBackStack()
                            },
                            onCloseClick = {
                                navController.popBackStack()
                            },
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}