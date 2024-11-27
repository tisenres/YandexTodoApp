package com.tisenres.yandextodoapp.di.modules

import com.tisenres.yandextodoapp.domain.repository.RevisionRepository
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import com.tisenres.yandextodoapp.domain.usecases.CreateTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodoItemUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetTodoItemUseCase(
        remoteRepository: TodoItemsRemoteRepository,
        localRepository: RevisionRepository
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
        localRepository: RevisionRepository
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
        localRepository: RevisionRepository
    ): CreateTodoUseCase {
        return CreateTodoUseCase(
            remoteRepository,
            localRepository
        )
    }
}