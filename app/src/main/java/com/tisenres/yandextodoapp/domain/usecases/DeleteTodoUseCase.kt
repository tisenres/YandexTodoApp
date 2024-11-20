package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.repository.TodoItemsLocalRepository
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val remoteRepository: TodoItemsRemoteRepository,
    private val localRepository: TodoItemsLocalRepository,
) {
    suspend operator fun invoke(todoId: String) {
        val currentRevision = localRepository.getCurrentRevision()
        val newRevision = remoteRepository.deleteTodoItem(todoId, currentRevision)

        localRepository.setCurrentRevision(newRevision)
    }
}