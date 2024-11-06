package com.tisenres.yandextodoapp.di

import com.tisenres.yandextodoapp.data.repository.TodoItemsRepositoryImpl
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTodoItemsRepository(
        impl: TodoItemsRepositoryImpl
    ): TodoItemsRepository
}
