package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsLocalRepository
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val remoteRepository: TodoItemsRemoteRepository,
    private val localRepository: TodoItemsLocalRepository
) {
    suspend operator fun invoke(todoItem: TodoItem) {
        val currentRevision = localRepository.getCurrentRevision()
        val newRevision = remoteRepository.updateTodoItem(todoItem, currentRevision)

        localRepository.setCurrentRevision(newRevision)
    }
}