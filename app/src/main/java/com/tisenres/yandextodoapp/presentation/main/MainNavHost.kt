package com.tisenres.yandextodoapp.presentation.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tisenres.yandextodoapp.di.components.LocalTodoDetailsComponent
import com.tisenres.yandextodoapp.di.components.LocalTodoListComponent
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsScreen
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsViewModel
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListScreen
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListViewModel
import kotlinx.coroutines.launch

@Composable
fun MainNavHost(
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    NavHost(
        navController = navController,
        startDestination = Screen.TodoList.route
    ) {
        composable(Screen.TodoList.route) {
            val listComponent = LocalTodoListComponent.current
            val viewModelFactory = listComponent.viewModelFactory()
            val todoListViewModel = ViewModelProvider(
                LocalViewModelStoreOwner.current!!,
                viewModelFactory
            ).get(TodoListViewModel::class.java)

            TodoListScreen(
                viewModel = todoListViewModel,
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

            val detailsComponent = LocalTodoDetailsComponent.current
            val viewModelFactory = detailsComponent.viewModelFactory()
            val viewModel = ViewModelProvider(
                LocalViewModelStoreOwner.current!!,
                viewModelFactory
            ).get(TodoDetailsViewModel::class.java)

            TodoDetailsScreen(
                todoId = todoId,
                isEditing = true,
                onSaveClick = { text, importance, deadline ->
                    lifecycleOwner.lifecycleScope.launch {
                        viewModel.updateTodoAsync(todoId, text, importance, deadline)
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

        composable(Screen.CreateTodo.route) { backStackEntry ->
            val detailsComponent = LocalTodoDetailsComponent.current
            val viewModelFactory = detailsComponent.viewModelFactory()
            val viewModel = ViewModelProvider(
                LocalViewModelStoreOwner.current!!,
                viewModelFactory
            ).get(TodoDetailsViewModel::class.java)

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