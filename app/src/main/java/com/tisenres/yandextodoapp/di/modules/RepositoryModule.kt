package com.tisenres.yandextodoapp.di.modules

import com.tisenres.yandextodoapp.data.local.repository.RevisionRepositoryImpl
import com.tisenres.yandextodoapp.data.repository.TodoItemsRemoteRepositoryImpl
import com.tisenres.yandextodoapp.domain.repository.RevisionRepository
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
        impl: RevisionRepositoryImpl
    ): RevisionRepository
}