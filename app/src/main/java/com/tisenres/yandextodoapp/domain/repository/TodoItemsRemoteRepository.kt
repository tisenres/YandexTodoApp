package com.tisenres.yandextodoapp.domain.repository

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRemoteRepository {
    suspend fun getAllTodos(): Flow<List<TodoItem>>
    suspend fun getTodoItemById(id: String): Flow<TodoItem?>
    suspend fun createTodo(item: TodoItem)
    suspend fun updateTodoItem(item: TodoItem)
    suspend fun deleteTodoItem(id: String)
    suspend fun updateAllTodos(todos: List<TodoItem>)
}
