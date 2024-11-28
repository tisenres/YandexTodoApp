package com.tisenres.yandextodoapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tisenres.yandextodoapp.di.ViewModelKey
import com.tisenres.yandextodoapp.di.scopes.TodoListScope
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListViewModel
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.Provides
import javax.inject.Provider

@Module
class TodoListModule {

    @Provides
    @IntoMap
    @ViewModelKey(TodoListViewModel::class)
    fun provideTodoListViewModel(viewModel: TodoListViewModel): ViewModel = viewModel

    @Provides
    @TodoListScope
    fun provideViewModelFactory(
        creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(creators)
    }
}