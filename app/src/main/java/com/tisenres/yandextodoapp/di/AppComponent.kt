package com.tisenres.yandextodoapp.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.tisenres.yandextodoapp.presentation.main.MainActivity
import com.tisenres.yandextodoapp.presentation.screens.tododetails.TodoDetailsViewModel
import com.tisenres.yandextodoapp.presentation.screens.todolist.TodoListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(viewModel: TodoListViewModel)
    fun inject(viewModel: TodoDetailsViewModel)

    fun getViewModelFactory(): ViewModelProvider.Factory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}