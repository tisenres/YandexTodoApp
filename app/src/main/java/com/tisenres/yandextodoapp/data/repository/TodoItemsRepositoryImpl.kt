package com.tisenres.yandextodoapp.data.repository

import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import java.text.SimpleDateFormat

class TodoItemsRepositoryImpl: TodoItemsRepository {

    val todoItems = mutableListOf<TodoItem>()
    val formatter = SimpleDateFormat("yyyy-MM-dd")

    override suspend fun getAllTodos(): Flow<List<TodoItem>> {
//        todoItems.add(
//            TodoItem(
//                id = "1",
//                text = "Task 1",
//                isCompleted = false,
//                createdAt = formatter.format(Date()),
//                modifiedAt = LocalDate.now(),
//                deadline = LocalDate.now().
//            )
//        )
        return emptyFlow()
    }

    override suspend fun getTodo(id: String): TodoItem? {
        return null
    }

    override suspend fun addTodo(item: TodoItem) {

    }

    override suspend fun deleteTodo(id: String) {

    }

}