package com.tisenres.yandextodoapp.data.repository

import com.tisenres.yandextodoapp.data.local.preference.AppPreference
import com.tisenres.yandextodoapp.data.remote.TodoApiService
import com.tisenres.yandextodoapp.data.remote.dto.TodoRequestDto
import com.tisenres.yandextodoapp.data.remote.mappers.toDomainModel
import com.tisenres.yandextodoapp.data.remote.mappers.toNetworkModel
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val todoApi: TodoApiService,
    private val appPreference: AppPreference
) : TodoItemsRepository {

    override fun getAllTodos(): Flow<List<TodoItem>> = flow {
        val response = todoApi.getTodos()
        appPreference.setCurrentRevision(response.revision)
        emit(response.todoList.map { it.toDomainModel() })

    }

    override suspend fun getTodoItemById(id: String): TodoItem? {
        return todoApi.getTodos().todoList.find { it.id.toString() == id }?.toDomainModel()
    }

    override suspend fun updateTodoItem(item: TodoItem) {
    }

    override suspend fun createTodo(item: TodoItem) {
        todoApi.createTodo(
            TodoRequestDto(item.toNetworkModel())
        )
    }

    override suspend fun deleteTodoItem(id: String) {

    }
}