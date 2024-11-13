package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val repository: TodoItemsRemoteRepository
) {
    suspend operator fun invoke(todoId: String) {
        repository.deleteTodoItem(todoId)
    }
}
