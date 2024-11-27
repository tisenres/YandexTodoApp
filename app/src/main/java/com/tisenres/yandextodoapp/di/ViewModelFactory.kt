package com.tisenres.yandextodoapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tisenres.yandextodoapp.domain.usecases.CreateTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodoItemUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodosUseCase
import com.tisenres.yandextodoapp.domain.usecases.UpdateTodoUseCase
import com.tisenres.yandextodoapp.presentation.main.NetworkChecker
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsViewModel
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val getTodoItemUseCase: GetTodoItemUseCase,
    private val createTodoUseCase: CreateTodoUseCase,
    private val networkChecker: NetworkChecker
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TodoListViewModel::class.java) -> {
                TodoListViewModel(
                    getTodosUseCase,
                    updateTodoUseCase,
                    deleteTodoUseCase,
                    networkChecker
                ) as T
            }
            modelClass.isAssignableFrom(TodoDetailsViewModel::class.java) -> {
                TodoDetailsViewModel(
                    getTodoItemUseCase,
                    createTodoUseCase,
                    deleteTodoUseCase,
                    updateTodoUseCase
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        }
    }
}