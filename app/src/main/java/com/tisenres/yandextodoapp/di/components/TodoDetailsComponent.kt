package com.tisenres.yandextodoapp.di.components

import com.tisenres.yandextodoapp.di.scopes.TodoDetailsScope
import dagger.Subcomponent
import androidx.lifecycle.ViewModelProvider
import com.tisenres.yandextodoapp.di.modules.TodoDetailsModule

@TodoDetailsScope
@Subcomponent(modules = [TodoDetailsModule::class])
interface TodoDetailsComponent {

    fun viewModelFactory(): ViewModelProvider.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(): TodoDetailsComponent
    }
}
