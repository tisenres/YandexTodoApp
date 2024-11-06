package com.tisenres.yandextodoapp.data.repository

import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor() : TodoItemsRepository {

    private val items = MutableStateFlow(generateMockData())

    override fun getAllTodos(): Flow<List<TodoItem>> = items.asStateFlow()

    override fun addTodoItem(item: TodoItem) {}

    override fun deleteTodoItem(id: String) {
        TODO("Not yet implemented")
    }

    private fun generateMockData(): List<TodoItem> {
        return List(20) {
            TodoItem(
                id = UUID.randomUUID().toString(),
                text = "Task ${it + 1}",
                importance = Importance.HIGH,
                isCompleted = false,
                createdAt = Date()
            )
        }
    }
}
