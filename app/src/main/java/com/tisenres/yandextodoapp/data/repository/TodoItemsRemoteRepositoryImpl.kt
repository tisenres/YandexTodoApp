package com.tisenres.yandextodoapp.data.repository

import com.tisenres.yandextodoapp.data.remote.TodoApiService
import com.tisenres.yandextodoapp.data.remote.dto.TodoRequestDto
import com.tisenres.yandextodoapp.data.remote.mappers.toDomainModel
import com.tisenres.yandextodoapp.data.remote.mappers.toNetworkModel
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class TodoItemsRemoteRepositoryImpl @Inject constructor(
    private val todoApi: TodoApiService,
) : TodoItemsRemoteRepository {

    override suspend fun getAllTodos(): Pair<Flow<List<TodoItem>>, Int> {
        val response = todoApi.getTodos()

        val todos = response.todoList?.map { it.toDomainModel() } ?: emptyList()
        val revision = response.revision

        val todosFlow = flow {
            emit(todos)
        }

        return todosFlow to revision
    }

    override suspend fun createTodo(item: TodoItem, revision: Int): Int {
        val response = todoApi.createTodo(
            request = TodoRequestDto(item.toNetworkModel()),
            revision = revision
        )
        return response.revision
    }

    override suspend fun getTodoItemById(id: String): Pair<Flow<TodoItem?>, Int> {
        val response = todoApi.getTodoById(
            todoId = UUID.fromString(id)
        )

        val todo = response.element?.toDomainModel()
        val revision = response.revision

        val todoFlow = flow {
            emit(todo)
        }

        return todoFlow to revision
    }

    override suspend fun updateTodoItem(item: TodoItem, revision: Int): Int {
        val response = todoApi.updateTodoById(
            request = TodoRequestDto(item.toNetworkModel()),
            todoId = item.id,
            revision = revision
        )

        return response.revision
    }

    override suspend fun deleteTodoItem(id: String, revision: Int): Int {
        val response = todoApi.deleteTodoById(
            todoId = id,
            revision = revision
        )

        return response.revision
    }

    override suspend fun updateAllTodos(todos: List<TodoItem>, revision: Int): Int {
        val networkTodos = todos.map { it.toNetworkModel() }
        val response = todoApi.updateTodos(
            request = networkTodos,
            revision = revision
        )

        return response.revision
    }
}