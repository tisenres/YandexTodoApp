package com.tisenres.yandextodoapp.app

import android.app.Application
import com.tisenres.yandextodoapp.di.components.AppComponent
import com.tisenres.yandextodoapp.di.components.DaggerAppComponent

class YandexTodoApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}
