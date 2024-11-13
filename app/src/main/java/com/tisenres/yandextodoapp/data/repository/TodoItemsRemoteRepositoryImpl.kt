package com.tisenres.yandextodoapp.data.repository

import com.tisenres.yandextodoapp.data.local.preference.AppPreference
import com.tisenres.yandextodoapp.data.remote.TodoApiService
import com.tisenres.yandextodoapp.data.remote.dto.TodoRequestDto
import com.tisenres.yandextodoapp.data.remote.mappers.toDomainModel
import com.tisenres.yandextodoapp.data.remote.mappers.toNetworkModel
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoItemsRemoteRepositoryImpl @Inject constructor(
    private val todoApi: TodoApiService,
    private val appPreference: AppPreference
) : TodoItemsRemoteRepository {

    override suspend fun getAllTodos(): Flow<List<TodoItem>> = flow {
        val response = todoApi.getTodos()
        appPreference.setCurrentRevision(response.revision)
        emit(response.todoList.map { it.toDomainModel() })
    }

    override suspend fun createTodo(item: TodoItem) {
        val response = todoApi.createTodo(
            TodoRequestDto(item.toNetworkModel())
        )
        appPreference.setCurrentRevision(response.revision)
    }

    override suspend fun getTodoItemById(id: String) = flow {
        val response = todoApi.getTodos()
        appPreference.setCurrentRevision(response.revision)
        emit(response.todoList.find { it.id.toString() == id }?.toDomainModel())
    }

    override suspend fun updateTodoItem(item: TodoItem) {
        val response = todoApi.updateTodoById(
            TodoRequestDto(item.toNetworkModel()),
            item.id
        )
        appPreference.setCurrentRevision(response.revision)
    }

    override suspend fun deleteTodoItem(id: String) {
        val response = todoApi.deleteTodoById(id)
        appPreference.setCurrentRevision(response.revision)
    }

    override suspend fun updateAllTodos(todos: List<TodoItem>) {
        val networkTodos = todos.map { it.toNetworkModel() }
        val response = todoApi.updateTodos(networkTodos)
        appPreference.setCurrentRevision(response.revision)
    }
}