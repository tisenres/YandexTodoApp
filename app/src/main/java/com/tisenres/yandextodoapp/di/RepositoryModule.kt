package com.tisenres.yandextodoapp.di

import com.tisenres.yandextodoapp.data.local.preference.AppPreference
import com.tisenres.yandextodoapp.data.remote.TodoApiService
import com.tisenres.yandextodoapp.data.repository.TodoItemsRepositoryImpl
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTodoItemsRepository(
        todoApi: TodoApiService,
        appPreference: AppPreference
    ): TodoItemsRepository
    {
        return TodoItemsRepositoryImpl(todoApi, appPreference)
    }
}