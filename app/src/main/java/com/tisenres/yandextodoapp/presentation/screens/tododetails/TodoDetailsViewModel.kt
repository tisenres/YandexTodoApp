package com.tisenres.yandextodoapp.presentation.screens.tododetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.usecases.AddTodoItemUseCase
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoItemUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodoItemUseCase
import com.tisenres.yandextodoapp.domain.usecases.UpdateTodoItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TodoDetailsViewModel @Inject constructor(
    private val getTodoItemUseCase: GetTodoItemUseCase,
    private val addTodoItemUseCase: AddTodoItemUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase,
    private val deleteTodoItemUseCase: DeleteTodoItemUseCase
) : ViewModel() {

    private val _todo = MutableStateFlow<TodoItem?>(null)
    val todo: StateFlow<TodoItem?> = _todo

    fun loadTodo(todoId: String) {
        viewModelScope.launch {
            val loadedTodo = getTodoItemUseCase(todoId)
            _todo.value = loadedTodo
        }
    }

    fun createTodo(text: String, importance: Importance, deadline: Date?) {
        viewModelScope.launch {
            val newTodo = TodoItem(
                id = UUID.randomUUID().toString(),
                text = text,
                importance = importance,
                isCompleted = false,
                deadline = deadline,
                createdAt = Date(),
                modifiedAt = null
            )
            addTodoItemUseCase(newTodo)
        }
    }

    fun updateTodo(todoId: String, text: String, importance: Importance, deadline: Date?) {
        viewModelScope.launch {
            val currentTodo = _todo.value
            if (currentTodo != null) {
                val updatedTodo = currentTodo.copy(
                    text = text,
                    importance = importance,
                    deadline = deadline,
                    modifiedAt = Date()
                )
                updateTodoItemUseCase(updatedTodo)
            }
        }
    }

    fun deleteTodo(todoId: String) {
        viewModelScope.launch {
            deleteTodoItemUseCase(todoId)
        }
    }
}