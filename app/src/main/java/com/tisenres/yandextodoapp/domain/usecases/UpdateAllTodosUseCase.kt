package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.RevisionRepository
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import javax.inject.Inject

class UpdateAllTodosUseCase @Inject constructor(
    private val remoteRepository: TodoItemsRemoteRepository,
    private val localRepository: RevisionRepository,
) {
    suspend operator fun invoke(todos: List<TodoItem>) {
        val currentRevision = localRepository.getCurrentRevision()
        val newRevision = remoteRepository.updateAllTodos(todos, currentRevision)

        localRepository.setCurrentRevision(newRevision)
    }
}