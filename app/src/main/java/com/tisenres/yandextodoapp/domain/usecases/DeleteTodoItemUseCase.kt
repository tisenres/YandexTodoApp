package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import java.util.UUID
import javax.inject.Inject

class DeleteTodoItemUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(todoId: String) {
        repository.deleteTodoItem(todoId)
    }
}
