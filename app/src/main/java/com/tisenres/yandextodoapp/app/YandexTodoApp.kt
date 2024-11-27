package com.tisenres.yandextodoapp.app

import android.app.Application
import com.tisenres.yandextodoapp.di.AppComponent
import com.tisenres.yandextodoapp.di.DaggerAppComponent

class YandexTodoApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}
