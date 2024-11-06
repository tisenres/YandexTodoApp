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

    private val _task = MutableStateFlow<TodoItem?>(null)
    val task: StateFlow<TodoItem?> = _task

    fun loadTask(taskId: String) {
        viewModelScope.launch {
            val loadedTask = getTodoItemUseCase(taskId)
            _task.value = loadedTask
        }
    }

    fun createTask(text: String, importance: Importance, deadline: Date?) {
        viewModelScope.launch {
            val newTask = TodoItem(
                id = UUID.randomUUID().toString(),
                text = text,
                importance = importance,
                isCompleted = false,
                deadline = deadline,
                createdAt = Date(),
                modifiedAt = null
            )
            addTodoItemUseCase(newTask)
        }
    }

    fun updateTask(taskId: String, text: String, importance: Importance, deadline: Date?) {
        viewModelScope.launch {
            val currentTask = _task.value
            if (currentTask != null) {
                val updatedTask = currentTask.copy(
                    text = text,
                    importance = importance,
                    deadline = deadline,
                    modifiedAt = Date()
                )
                updateTodoItemUseCase(updatedTask)
            }
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            deleteTodoItemUseCase(taskId)
        }
    }
}