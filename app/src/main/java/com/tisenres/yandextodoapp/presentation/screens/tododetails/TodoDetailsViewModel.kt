package com.tisenres.yandextodoapp.presentation.screens.tododetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisenres.yandextodoapp.data.remote.interceptors.BadRequestException
import com.tisenres.yandextodoapp.domain.entity.Importance
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import com.tisenres.yandextodoapp.domain.usecases.CreateTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodoItemUseCase
import com.tisenres.yandextodoapp.domain.usecases.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
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
            } catch (e: BadRequestException) {
                _errorMessage.value = "Bad Request: ${e.message}"
            } catch (e: Exception) {
                _errorMessage.value = "Something went wrong"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createTodoAsync(text: String, importance: Importance, deadline: Date?): Deferred<Unit> {
        return viewModelScope.async {
            _isLoading.value = true
            try {
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
            } catch (e: BadRequestException) {
                _errorMessage.value = "Bad Request: ${e.message}"
            } catch (e: Exception) {
                _errorMessage.value = "Something went wrong"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateTodoAsync(todoId: String, text: String, importance: Importance, deadline: Date?): Deferred<Unit> {
        return viewModelScope.async {
            _isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    val updatedTodo = TodoItem(
                        id = todoId,
                        text = text,
                        importance = importance,
                        deadline = deadline,
                        isCompleted = _todo.value?.isCompleted ?: false,
                        createdAt = _todo.value?.createdAt ?: Date(),
                        modifiedAt = Date()
                    )
                    updateTodoUseCase(updatedTodo)
                }
            } catch (e: BadRequestException) {
                _errorMessage.value = "Bad Request: ${e.message}"
            } catch (e: Exception) {
                _errorMessage.value = "Something went wrong"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteTodoAsync(todoId: String): Deferred<Unit> {
        return viewModelScope.async {
            _isLoading.value = true
            try {
                withContext(Dispatchers.IO) {
                    deleteTodoUseCase(todoId)
                }
            } catch (e: BadRequestException) {
                _errorMessage.value = "Bad Request: ${e.message}"
            } catch (e: Exception) {
                _errorMessage.value = "Something went wrong"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}