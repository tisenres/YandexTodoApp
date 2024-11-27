package com.tisenres.yandextodoapp.presentation.main

sealed class Screen(val route: String) {
    data object TodoList : Screen("todoList")
    data object TodoDetails : Screen("todoDetails/{todoId}")
    data object CreateTodo : Screen("createTodo")
}
