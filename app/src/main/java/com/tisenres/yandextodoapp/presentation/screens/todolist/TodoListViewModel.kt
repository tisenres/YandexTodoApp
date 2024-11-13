package com.tisenres.yandextodoapp.presentation.screens.todolist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.usecases.GetTodosUseCase
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
    private val getTodosUseCase: GetTodosUseCase
) : ViewModel() {

    private val _todos: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error.asStateFlow()

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
                _error.value = e.message
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
        _todos.value = _todos.value.map { todo ->
            if (todo.id == todoId) {
                todo.copy(isCompleted = true)
            } else {
                todo
            }
        }
    }

    fun deleteTodo(todoId: String) {
        _todos.value = _todos.value.filterNot { it.id == todoId }
    }
}