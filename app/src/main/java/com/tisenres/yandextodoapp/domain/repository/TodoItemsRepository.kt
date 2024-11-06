package com.tisenres.yandextodoapp.domain.repository

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    fun getAllTodos(): Flow<List<TodoItem>>
    fun addTodoItem(item: TodoItem)
    fun deleteTodoItem(id: String)
}