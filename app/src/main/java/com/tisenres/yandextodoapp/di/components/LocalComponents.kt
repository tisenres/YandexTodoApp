package com.tisenres.yandextodoapp.di.components

import androidx.compose.runtime.staticCompositionLocalOf

val LocalTodoListComponent = staticCompositionLocalOf<TodoListComponent> {
    error("No TodoListComponent provided")
}

val LocalTodoDetailsComponent = staticCompositionLocalOf<TodoDetailsComponent> {
    error("No TodoDetailsComponent provided")
}
