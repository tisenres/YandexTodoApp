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
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : ViewModel() {

    private val _todo = MutableStateFlow<TodoItem?>(null)
    val todo: StateFlow<TodoItem?> = _todo.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun getTodoById(todoId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    getTodoItemUseCase(todoId).collect { todo ->
                        _todo.value = todo
                    }
                }
            } catch (e: Exception) {
                Log.e("TodoDetailsViewModel", "Error fetching todo by id", e)
                _errorMessage.value = e.message ?: "Error fetching todo"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createTodo(text: String, importance: Importance, deadline: Date?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    createTodoUseCase(
                        TodoItem(
                            id = UUID.randomUUID().toString(),
                            text = text,
                            importance = importance,
                            deadline = deadline,
                            isCompleted = false,
                            createdAt = Date(),
                            modifiedAt = null
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("TodoDetailsViewModel", "Error creating todo", e)
                _errorMessage.value = e.message ?: "Error creating todo"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateTodo(todoId: String, text: String, importance: Importance, deadline: Date?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    _todo.value?.let { todo ->
                        val updatedTodo = todo.copy(
                            text = text,
                            importance = importance,
                            deadline = deadline,
                            modifiedAt = Date()
                        )
                        updateTodoUseCase(updatedTodo)
                    }
                }
            } catch (e: Exception) {
                Log.e("TodoDetailsViewModel", "Error updating todo", e)
                _errorMessage.value = e.message ?: "Error updating todo"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteTodo(todoId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    deleteTodoUseCase(todoId)
                }
            } catch (e: Exception) {
                Log.e("TodoDetailsViewModel", "Error deleting todo", e)
                _errorMessage.value = e.message ?: "Error deleting todo"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}