package com.tisenres.yandextodoapp.di

import android.content.Context
import com.tisenres.yandextodoapp.data.local.preference.AppPreference
import com.tisenres.yandextodoapp.data.remote.TodoApi
import com.tisenres.yandextodoapp.data.repository.TodoItemsRepositoryImpl
import com.tisenres.yandextodoapp.domain.repository.TodoItemsRepository
import com.tisenres.yandextodoapp.domain.usecases.AddTodoItemUseCase
import com.tisenres.yandextodoapp.domain.usecases.DeleteTodoItemUseCase
import com.tisenres.yandextodoapp.domain.usecases.GetTodoItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideTodoItemsRepository(
//        todoApi: TodoApi
//    ): TodoItemsRepository {
//        return TodoItemsRepositoryImpl(todoApi)
//    }
//
//    @Provides
//    fun provideGetTodoItemUseCase(repository: TodoItemsRepository): GetTodoItemUseCase {
//        return GetTodoItemUseCase(repository)
//    }
//
//    @Provides
//    fun provideDeleteTodoItemUseCase(repository: TodoItemsRepository): DeleteTodoItemUseCase {
//        return DeleteTodoItemUseCase(repository)
//    }
//
//    @Provides
//    fun provideAddTodoItemUseCase(repository: TodoItemsRepository): AddTodoItemUseCase {
//        return AddTodoItemUseCase(repository)
//    }

    @Provides
    @Singleton
    fun provideAppPreference(@ApplicationContext context: Context): AppPreference {
        return AppPreference(context)
    }
}
