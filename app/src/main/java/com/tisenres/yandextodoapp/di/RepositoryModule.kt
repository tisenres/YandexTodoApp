package com.tisenres.yandextodoapp.di

import com.tisenres.yandextodoapp.data.local.preference.AppPreferences
import com.tisenres.yandextodoapp.data.local.repository.TodoItemsLocalRepositoryImpl
import com.tisenres.yandextodoapp.data.remote.TodoApiService
import com.tisenres.yandextodoapp.data.repository.TodoItemsRemoteRepositoryImpl
import com.tisenres.yandextodoapp.domain.repository.TodoItemsLocalRepository
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTodoItemsRemoteRepository(
        todoApi: TodoApiService,
    ): TodoItemsRemoteRepository {
        return TodoItemsRemoteRepositoryImpl(todoApi)
    }

    @Provides
    @Singleton
    fun provideTodoItemsLocalRepository(
        appPreferences: AppPreferences
    ): TodoItemsLocalRepository {
        return TodoItemsLocalRepositoryImpl(appPreferences)
    }
}