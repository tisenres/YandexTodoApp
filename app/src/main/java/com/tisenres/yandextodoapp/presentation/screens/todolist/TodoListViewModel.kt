package com.tisenres.yandextodoapp.presentation.screens.todolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisenres.yandextodoapp.data.remote.interceptors.BadRequestException
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodosUseCase
import com.tisenres.yandextodoapp.domain.usecases.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {

    private val _onShowCompletedTasks = MutableStateFlow(false)
    val onShowCompletedTasks: StateFlow<Boolean> = _onShowCompletedTasks.asStateFlow()

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _todos: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    init {
        getAllTodos()
    }

    private fun getAllTodos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    getTodosUseCase().collect { todosList ->
                        _todos.value = todosList
                    }
                }
                _isError.value = false
            } catch (e: BadRequestException) {
                _errorMessage.value = "Bad Request: ${e.message}"
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error fetching todos", e)
                _errorMessage.value = "Something went wrong"
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshTodosWithRetry() {
        viewModelScope.launch {
            var retryCount = 0
            val maxRetries = 3
            val delayMillis = 2000L
            _isLoading.value = true
            while (retryCount < maxRetries) {
                try {
                    withContext(Dispatchers.IO) {
                        getTodosUseCase().collect { todosList ->
                            _todos.value = todosList
                        }
                    }
                    _isError.value = false
                    break
                } catch (e: BadRequestException) {
                    _errorMessage.value = "Bad Request: ${e.message}"
                    retryCount++
                } catch (e: Exception) {
                    retryCount++
                    Log.e("TodoListViewModel", "Error fetching todos, retry $retryCount", e)
                    if (retryCount == maxRetries) {
                        _errorMessage.value = "Error fetching todos"
                        _isError.value = true
                    } else {
                        delay(delayMillis)
                    }
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun completeTodo(todoId: String) {
        viewModelScope.launch {
            Log.d("TodoListViewModel", "completeTodo: $todoId")
            try {
                val todo = _todos.value.find { it.id == todoId }

                if (todo != null) {
                    val updatedTodo = todo.copy(isCompleted = !todo.isCompleted)

                    withContext(Dispatchers.IO) {
                        updateTodoUseCase(updatedTodo)
                    }

                    _todos.value = _todos.value.map {
                        if (it.id == todoId) updatedTodo else it
                    }
                }
            } catch (e: BadRequestException) {
                _errorMessage.value = "Bad Request: ${e.message}"
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error completing todo", e)
                _errorMessage.value = "Something went wrong"
            }
        }
    }

    fun deleteTodo(todoId: String) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    deleteTodoUseCase(todoId)
                }

                _todos.value = _todos.value.filterNot { it.id == todoId }
            } catch (e: BadRequestException) {
                _errorMessage.value = "Bad Request: ${e.message}"
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error deleting todo", e)
                _errorMessage.value = "Error deleting todo"
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun setNetworkAvailable(isAvailable: Boolean) {
        _isConnected.value = isAvailable
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun toggleEyeButton() {
        _onShowCompletedTasks.value = !_onShowCompletedTasks.value
    }
}