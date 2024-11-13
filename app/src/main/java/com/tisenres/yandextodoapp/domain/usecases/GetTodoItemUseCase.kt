package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoItemUseCase @Inject constructor(
    private val repository: TodoItemsRepository
) {
    suspend operator fun invoke(todoId: String): Flow<TodoItem?> {
        return repository.getTodoItemById(todoId)
    }
}
