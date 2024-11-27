package com.tisenres.yandextodoapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tisenres.yandextodoapp.di.ViewModelKey
import com.tisenres.yandextodoapp.di.scopes.TodoDetailsScope
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TodoDetailsModule {

    @Binds
    @IntoMap
    @ViewModelKey(TodoDetailsViewModel::class)
    abstract fun bindTodoDetailsViewModel(viewModel: TodoDetailsViewModel): ViewModel

    @Binds
    @TodoDetailsScope
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
