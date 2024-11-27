package com.tisenres.yandextodoapp.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.tisenres.yandextodoapp.data.local.preference.AppPreferences
import com.tisenres.yandextodoapp.domain.usecases.*
import com.tisenres.yandextodoapp.presentation.main.NetworkChecker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkChecker(application: Application): NetworkChecker {
        return NetworkChecker(application)
    }

    @Provides
    @Singleton
    fun provideAppPreferences(application: Application): AppPreferences {
        return AppPreferences(application)
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(
        getTodosUseCase: GetTodosUseCase,
        updateTodoUseCase: UpdateTodoUseCase,
        deleteTodoUseCase: DeleteTodoUseCase,
        getTodoItemUseCase: GetTodoItemUseCase,
        createTodoUseCase: CreateTodoUseCase,
        networkChecker: NetworkChecker
    ): ViewModelProvider.Factory {
        return ViewModelFactory(
            getTodosUseCase,
            updateTodoUseCase,
            deleteTodoUseCase,
            getTodoItemUseCase,
            createTodoUseCase,
            networkChecker
        )
    }
}