package com.tisenres.yandextodoapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListScreen
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsScreen
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsViewModel
import com.tisenres.yandextodoapp.presentation.theme.YandexTodoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                            onTodoClick = { todoId ->
                                navController.navigate("todoDetails/$todoId")
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
                        )
                    ) { backStackEntry ->
                        val todoId =
                            backStackEntry.arguments?.getString("todoId") ?: return@composable
                        val viewModel = hiltViewModel<TodoDetailsViewModel>()

                        TodoDetailsScreen(
                            todoId = todoId,
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
                            viewModel = viewModel
                        )
                    }

                    composable("createTodo") {
                        val viewModel = hiltViewModel<TodoDetailsViewModel>()

                        TodoDetailsScreen(
                            todoId = "",
                            isEditing = false,
                            onSaveClick = { text, importance, deadline ->
                                lifecycleScope.launch {
                                    viewModel.createTodoAsync(text, importance, deadline).await()
                                    navController.popBackStack()
                                }
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