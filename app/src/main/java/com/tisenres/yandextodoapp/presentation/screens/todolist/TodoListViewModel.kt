package com.tisenres.yandextodoapp.presentation.screens.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisenres.yandextodoapp.domain.entity.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoListModel: TodoListModel
): ViewModel() {

    private val _todos: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos

    private fun getAllTodos() {
        viewModelScope.launch {
            todoListModel.getAllTodos()
        }
    }

}
