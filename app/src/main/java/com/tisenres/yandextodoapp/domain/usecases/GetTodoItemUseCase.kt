package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsLocalRepository
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoItemUseCase @Inject constructor(
    private val remoteRepository: TodoItemsRemoteRepository,
    private val localRepository: TodoItemsLocalRepository
) {
    suspend operator fun invoke(todoId: String): Flow<TodoItem?> {
        val pair = remoteRepository.getTodoItemById(todoId)

        val todos = pair.first
        val revision = pair.second

        localRepository.setCurrentRevision(revision)
        return todos
    }
}