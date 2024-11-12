package com.tisenres.yandextodoapp.data.repository

import com.tisenres.yandextodoapp.data.remote.TodoApi
import com.tisenres.yandextodoapp.data.remote.mappers.toDomainModel
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import java.util.Date
import java.util.Random
import java.util.UUID
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val todoApi: TodoApi
) : TodoItemsRepository {

    private val items = MutableStateFlow(generateMockData())

    override fun getAllTodos(): Flow<List<TodoItem>> = flow {
        todoApi.getTodos().todoList.map {it.toDomainModel()}
    }

    override suspend fun getTodoItemById(id: String): TodoItem? {
        return items.value.find { it.id == id }
    }

    override suspend fun updateTodoItem(item: TodoItem) {
    }

    override suspend fun addTodoItem(item: TodoItem) {
        items.value += item
    }

    override suspend fun deleteTodoItem(id: String) {
        items.value = items.value.filter { it.id != id }
    }

    private fun generateMockData(): List<TodoItem> {
        return List(20) {
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Todo ${it + 1}",
                importance = Importance.entries[Random().nextInt(3)],
                isCompleted = false,
                createdAt = Date()
            )
        }
    }
}
