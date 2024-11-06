//package com.tisenres.yandextodoapp.presentation.screens.todolist
//
//import com.tisenres.yandextodoapp.domain.entity.TodoItem
//import com.tisenres.yandextodoapp.domain.usecases.GetTodosUseCase
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class TodoListModel @Inject constructor(
//    private val getTodosUseCase: GetTodosUseCase
//) {
//    suspend fun getAllTodos(): Flow<List<TodoItem>> {
//        return getTodosUseCase()
//    }
//}