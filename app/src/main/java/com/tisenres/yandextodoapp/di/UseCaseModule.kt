package com.tisenres.yandextodoapp.di

import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import com.tisenres.yandextodoapp.domain.usecases.CreateTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodoItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetTodoItemUseCase(repository: TodoItemsRemoteRepository): GetTodoItemUseCase {
        return GetTodoItemUseCase(repository)
    }

    @Provides
    fun provideDeleteTodoItemUseCase(repository: TodoItemsRemoteRepository): DeleteTodoUseCase {
        return DeleteTodoUseCase(repository)
    }

    @Provides
    fun provideAddTodoItemUseCase(repository: TodoItemsRemoteRepository): CreateTodoUseCase {
        return CreateTodoUseCase(repository)
    }
}
