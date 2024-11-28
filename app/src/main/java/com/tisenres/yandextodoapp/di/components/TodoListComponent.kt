package com.tisenres.yandextodoapp.di.components

import com.tisenres.yandextodoapp.di.scopes.TodoListScope
import dagger.Subcomponent
import androidx.lifecycle.ViewModelProvider
import com.tisenres.yandextodoapp.di.modules.TodoListModule

@TodoListScope
@Subcomponent(modules = [TodoListModule::class])
interface TodoListComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(): TodoListComponent
    }
}

