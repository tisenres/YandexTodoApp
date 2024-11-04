package com.tisenres.yandextodoapp.domain.repository

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    suspend fun getAllTodos(): Flow<List<TodoItem>>
    suspend fun getTodo(id: String): TodoItem?
    suspend fun addTodo(item: TodoItem)
    suspend fun deleteTodo(id: String)
}