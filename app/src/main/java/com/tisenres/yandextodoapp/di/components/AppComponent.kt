package com.tisenres.yandextodoapp.di.components

import android.app.Application
import com.tisenres.yandextodoapp.di.modules.*
import com.tisenres.yandextodoapp.presentation.main.MainActivity
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

    fun listComponent(): TodoListComponent.Factory
    fun detailsComponent(): TodoDetailsComponent.Factory

    fun inject(application: Application)
    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}