package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val repository: TodoItemsRemoteRepository
) {
    suspend operator fun invoke(todoItem: TodoItem) {
        repository.updateTodoItem(todoItem)
    }
}
