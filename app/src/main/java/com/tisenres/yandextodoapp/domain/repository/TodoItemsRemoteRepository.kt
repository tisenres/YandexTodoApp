package com.tisenres.yandextodoapp.domain.repository

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRemoteRepository {
    suspend fun getAllTodos(): Pair<Flow<List<TodoItem>>, Int>
    suspend fun getTodoItemById(id: String): Pair<Flow<TodoItem?>, Int>
    suspend fun createTodo(item: TodoItem, revision: Int): Int
    suspend fun updateTodoItem(item: TodoItem, revision: Int): Int
    suspend fun deleteTodoItem(id: String, revision: Int): Int
    suspend fun updateAllTodos(todos: List<TodoItem>, revision: Int): Int
}
