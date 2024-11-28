package com.tisenres.yandextodoapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tisenres.yandextodoapp.di.ViewModelKey
import com.tisenres.yandextodoapp.di.scopes.TodoDetailsScope
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsViewModel
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.Provides
import javax.inject.Provider

@Module
class TodoDetailsModule {

    @Provides
    @IntoMap
    @ViewModelKey(TodoDetailsViewModel::class)
    fun provideTodoDetailsViewModel(viewModel: TodoDetailsViewModel): ViewModel = viewModel

    @Provides
    @TodoDetailsScope
    fun provideViewModelFactory(
        creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return ViewModelFactory(creators)
    }
}