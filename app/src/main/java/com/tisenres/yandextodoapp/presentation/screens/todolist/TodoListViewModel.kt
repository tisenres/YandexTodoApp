package com.tisenres.yandextodoapp.presentation.screens.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisenres.yandextodoapp.data.remote.interceptors.BadRequestException
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodosUseCase
import com.tisenres.yandextodoapp.domain.usecases.UpdateTodoUseCase
import com.tisenres.yandextodoapp.presentation.main.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _onShowCompletedTasks = MutableStateFlow(false)
    val onShowCompletedTasks: StateFlow<Boolean> = _onShowCompletedTasks.asStateFlow()

    private val _todos: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val isConnected: StateFlow<Boolean> = networkChecker.isConnected

    init {
        monitorNetworkAndFetchTodos()
        getAllTodos()
    }

    private fun monitorNetworkAndFetchTodos() {
        viewModelScope.launch {
            isConnected.collect { connected ->
                if (connected) {
                    refreshTodosWithRetry()
                } else {
                    _errorMessage.value = "No internet connection"
                }
            }
        }
    }

    private fun getAllTodos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val todosList = withContext(Dispatchers.IO) {
                    getTodosUseCase().first()
                }
                _todos.value = todosList
                _isError.value = false
            } catch (e: BadRequestException) {
                _errorMessage.value = "Bad Request: ${e.message}"
                _isError.value = true
            } catch (e: Exception) {
                _errorMessage.value = "Something went wrong"
                _isError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshTodosWithRetry() {
        if (_isLoading.value) return

        viewModelScope.launch {
            var retryCount = 0
            val maxRetries = 3
            val delayMillis = 2000L
            _isLoading.value = true
            _errorMessage.value = null

            while (retryCount < maxRetries) {
                try {
                    val todosList = withContext(Dispatchers.IO) { getTodosUseCase().first() }
                    if (todosList.isNotEmpty()) {
                        _todos.value = todosList
                        _isError.value = false
                    } else {
                        _errorMessage.value = "No tasks available."
                        _isError.value = true
                    }
                    break
                } catch (e: BadRequestException) {
                    _errorMessage.value = "Bad Request: ${e.message}"
                    retryCount++
                } catch (e: Exception) {
                    retryCount++
                    _errorMessage.value = if (retryCount == maxRetries) {
                        "Failed to fetch todos after $maxRetries attempts."
                    } else {
                        "Error fetching todos. Retrying..."
                    }
                    if (retryCount < maxRetries) {
                        delay(delayMillis)
                    }
                }
            }

            if (retryCount == maxRetries) {
                _isError.value = true
            }

            _isLoading.value = false
        }
    }

    fun completeTodo(todoId: String) {
        viewModelScope.launch {
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
                _errorMessage.value = "Error deleting todo"
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        networkChecker.cleanup()
        viewModelScope.cancel()
    }

    fun toggleEyeButton() {
        _onShowCompletedTasks.value = !_onShowCompletedTasks.value
    }
}