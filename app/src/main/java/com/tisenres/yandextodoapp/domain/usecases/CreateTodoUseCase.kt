package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import javax.inject.Inject

class CreateTodoUseCase @Inject constructor(
    private val repository: TodoItemsRemoteRepository
) {
    suspend operator fun invoke(todoItem: TodoItem) {
        repository.createTodo(todoItem)
    }
}
