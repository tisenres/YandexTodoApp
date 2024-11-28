package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.RevisionRepository
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val remoteRepository: TodoItemsRemoteRepository,
    private val localRepository: RevisionRepository
) {
    suspend operator fun invoke(): Flow<List<TodoItem>> {
        val pair = remoteRepository.getAllTodos()

        val todos = pair.first
        val revision = pair.second

        localRepository.setCurrentRevision(revision)

        return todos
    }
}