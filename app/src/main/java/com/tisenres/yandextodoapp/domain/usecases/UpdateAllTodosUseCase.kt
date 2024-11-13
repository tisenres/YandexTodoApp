package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import javax.inject.Inject

class UpdateAllTodosUseCase @Inject constructor(
    private val todoRepository: TodoItemsRepository
) {
    suspend operator fun invoke(todos: List<TodoItem>) {
        todoRepository.updateAllTodos(todos)
    }
}