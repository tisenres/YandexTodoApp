package com.tisenres.yandextodoapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tisenres.yandextodoapp.di.ViewModelKey
import com.tisenres.yandextodoapp.di.scopes.TodoListScope
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TodoListModule {

    @Binds
    @IntoMap
    @ViewModelKey(TodoListViewModel::class)
    abstract fun bindTodoListViewModel(viewModel: TodoListViewModel): ViewModel

    @Binds
    @TodoListScope
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
