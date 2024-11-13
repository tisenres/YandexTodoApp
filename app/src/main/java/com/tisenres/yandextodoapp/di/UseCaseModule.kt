package com.tisenres.yandextodoapp.di

import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import com.tisenres.yandextodoapp.domain.usecases.AddTodoItemUseCase
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoItemUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodoItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetTodoItemUseCase(repository: TodoItemsRepository): GetTodoItemUseCase {
        return GetTodoItemUseCase(repository)
    }

    @Provides
    fun provideDeleteTodoItemUseCase(repository: TodoItemsRepository): DeleteTodoItemUseCase {
        return DeleteTodoItemUseCase(repository)
    }

    @Provides
    fun provideAddTodoItemUseCase(repository: TodoItemsRepository): AddTodoItemUseCase {
        return AddTodoItemUseCase(repository)
    }
}
