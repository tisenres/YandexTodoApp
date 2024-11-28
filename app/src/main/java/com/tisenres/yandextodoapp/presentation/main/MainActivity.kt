package com.tisenres.yandextodoapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.tisenres.yandextodoapp.app.YandexTodoApp
import com.tisenres.yandextodoapp.di.components.AppComponent
import com.tisenres.yandextodoapp.di.components.LocalTodoDetailsComponent
import com.tisenres.yandextodoapp.di.components.LocalTodoListComponent
import com.tisenres.yandextodoapp.di.components.TodoDetailsComponent
import com.tisenres.yandextodoapp.di.components.TodoListComponent
import com.tisenres.yandextodoapp.presentation.theme.YandexTodoAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var appComponent: AppComponent
    private lateinit var listComponent: TodoListComponent
    private lateinit var detailsComponent: TodoDetailsComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent = (application as YandexTodoApp).appComponent
        appComponent.inject(this)

        listComponent = appComponent.listComponent().create()
        detailsComponent = appComponent.detailsComponent().create()

        setContent {
            CompositionLocalProvider(
                LocalTodoListComponent provides listComponent,
                LocalTodoDetailsComponent provides detailsComponent
            ) {
                YandexTodoAppTheme {
                    val navController = rememberNavController()
                    MainNavHost(navController = navController)
                }
            }
        }
    }
}