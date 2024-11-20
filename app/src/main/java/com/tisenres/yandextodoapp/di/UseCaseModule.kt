package com.tisenres.yandextodoapp.di

import com.tisenres.yandextodoapp.domain.repository.TodoItemsLocalRepository
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import com.tisenres.yandextodoapp.domain.usecases.CreateTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodoItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTodoItemUseCase(
        remoteRepository: TodoItemsRemoteRepository,
        localRepository: TodoItemsLocalRepository
    ): GetTodoItemUseCase {
        return GetTodoItemUseCase(
            remoteRepository,
            localRepository
        )
    }

    @Provides
    @Singleton
    fun provideDeleteTodoItemUseCase(
        remoteRepository: TodoItemsRemoteRepository,
        localRepository: TodoItemsLocalRepository
    ): DeleteTodoUseCase {
        return DeleteTodoUseCase(
            remoteRepository,
            localRepository
        )
    }

    @Provides
    @Singleton
    fun provideAddTodoItemUseCase(
        remoteRepository: TodoItemsRemoteRepository,
        localRepository: TodoItemsLocalRepository
    ): CreateTodoUseCase {
        return CreateTodoUseCase(
            remoteRepository,
            localRepository
        )
    }
}