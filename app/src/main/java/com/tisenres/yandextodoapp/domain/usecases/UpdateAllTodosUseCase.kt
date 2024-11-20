package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import javax.inject.Inject

class UpdateAllTodosUseCase @Inject constructor(
    private val todoRepository: TodoItemsRemoteRepository
) {
    suspend operator fun invoke(todos: List<TodoItem>) {
        todoRepository.updateAllTodos(todos)
    }
}