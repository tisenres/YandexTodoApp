package com.tisenres.yandextodoapp.di.modules

import android.app.Application
import com.tisenres.yandextodoapp.data.local.preference.AppPreferences
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
}