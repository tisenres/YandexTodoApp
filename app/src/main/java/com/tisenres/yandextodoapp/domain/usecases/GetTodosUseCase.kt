package com.tisenres.yandextodoapp.domain.usecases

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val todoRepository: TodoItemsRemoteRepository
) {
    suspend operator fun invoke(): Flow<List<TodoItem>> {
        return todoRepository.getAllTodos()
    }
}