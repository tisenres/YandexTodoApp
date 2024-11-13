package com.tisenres.yandextodoapp.presentation.screens.todolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.usecases.CreateTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodosUseCase
import com.tisenres.yandextodoapp.domain.usecases.UpdateAllTodosUseCase
import com.tisenres.yandextodoapp.domain.usecases.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val createTodoUseCase: CreateTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val updateAllTodosUseCase: UpdateAllTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {

    private val _todos: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

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
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error fetching todos", e)
                _errorMessage.value = e.message ?: "Error fetching todos"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createTodo(todo: TodoItem) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    createTodoUseCase(todo)
                }
                getAllTodos()
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error creating todo", e)
                _errorMessage.value = e.message ?: "Error creating todo"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    updateTodoUseCase(todo)
                }
                // Update local state
                _todos.value = _todos.value.map {
                    if (it.id == todo.id) todo else it
                }
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error updating todo", e)
                _errorMessage.value = e.message ?: "Error updating todo"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateAllTodos(todos: List<TodoItem>) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    updateAllTodosUseCase(todos)
                }
                _todos.value = todos
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error updating all todos", e)
                _errorMessage.value = e.message ?: "Error updating todos"
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
                _todos.value = _todos.value.filterNot { it.id == todoId }
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error deleting todo", e)
                _errorMessage.value = e.message ?: "Error deleting todo"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshTodos() {
        viewModelScope.launch {
            getAllTodos()
        }
    }

    fun completeTodo(todoId: String) {
        viewModelScope.launch {
            try {
                _todos.value.find { it.id == todoId }?.let { todo ->
                    val updatedTodo = todo.copy(isCompleted = !todo.isCompleted)
                    updateTodo(updatedTodo)
                }
            } catch (e: Exception) {
                Log.e("TodoListViewModel", "Error completing todo", e)
                _errorMessage.value = e.message ?: "Error completing todo"
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}