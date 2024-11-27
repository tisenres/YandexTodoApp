package com.tisenres.yandextodoapp.di.modules

import com.tisenres.yandextodoapp.data.local.repository.TodoItemsLocalRepositoryImpl
import com.tisenres.yandextodoapp.data.repository.TodoItemsRemoteRepositoryImpl
import com.tisenres.yandextodoapp.domain.repository.TodoItemsLocalRepository
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTodoItemsRemoteRepository(
        impl: TodoItemsRemoteRepositoryImpl
    ): TodoItemsRemoteRepository

    @Binds
    @Singleton
    abstract fun bindTodoItemsLocalRepository(
        impl: TodoItemsLocalRepositoryImpl
    ): TodoItemsLocalRepository
}