package com.tisenres.yandextodoapp.presentation.main
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListScreen
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsScreen
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun MainNavHost(
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    NavHost(
        navController = navController,
        startDestination = "todoList"
    ) {
        composable(Screen.TodoList.route) {
            TodoListScreen(
                onTodoItemClick = { todoId ->
                    navController.navigate("todoDetails/$todoId")
                },
                onCreateTodoClick = {
                    navController.navigate("createTodo")
                }
            )
        }

        composable(
            Screen.TodoDetails.route,
            arguments = listOf(navArgument("todoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId") ?: return@composable
            val viewModel = hiltViewModel<TodoDetailsViewModel>()

            TodoDetailsScreen(
                todoId = todoId,
                isEditing = true,
                onSaveClick = { text, importance, deadline ->
                    lifecycleOwner.lifecycleScope.launch {
                        viewModel.updateTodoAsync(todoId, text, importance, deadline).await()
                        navController.popBackStack()
                    }
                },
                onDeleteClick = {
                    lifecycleOwner.lifecycleScope.launch {
                        viewModel.deleteTodoAsync(todoId).await()
                        navController.popBackStack()
                    }
                },
                onCloseClick = {
                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }

        composable(Screen.CreateTodo.route) {
            val viewModel = hiltViewModel<TodoDetailsViewModel>()

            TodoDetailsScreen(
                todoId = "",
                isEditing = false,
                onSaveClick = { text, importance, deadline ->
                    lifecycleOwner.lifecycleScope.launch {
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