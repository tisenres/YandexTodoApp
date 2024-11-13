package com.tisenres.yandextodoapp.presentation.screens.tododetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.usecases.CreateTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodoItemUseCase
import com.tisenres.yandextodoapp.domain.usecases.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TodoDetailsViewModel @Inject constructor(
    private val getTodoItemUseCase: GetTodoItemUseCase,
    private val createTodoUseCase: CreateTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {

    private val _todo = MutableStateFlow<TodoItem?>(null)
    val todo: StateFlow<TodoItem?> = _todo

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

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
            createTodoUseCase(newTodo)
        }
    }

    fun getTodoById(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val todo = withContext(Dispatchers.IO) {
                    getTodoItemUseCase(id)
                }
                todo?.let { updatedTodo ->
                    _todo.value = updatedTodo
                }
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error fetching todo by id", e)
                _errorMessage.value = e.message ?: "Error fetching todo"
            } finally {
                _isLoading.value = false
            }
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
                updateTodoUseCase(updatedTodo)
            }
        }
    }

    fun deleteTodo(todoId: String) {
        viewModelScope.launch {
            deleteTodoUseCase(todoId)
        }
    }
}