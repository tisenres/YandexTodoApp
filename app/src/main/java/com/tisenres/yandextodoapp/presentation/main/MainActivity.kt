package com.tisenres.yandextodoapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.*
import com.tisenres.yandextodoapp.app.YandexTodoApp
import com.tisenres.yandextodoapp.presentation.theme.YandexTodoAppTheme
import javax.inject.Inject
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as YandexTodoApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(LocalViewModelFactory provides viewModelFactory) {
                YandexTodoAppTheme {
                    val navController = rememberNavController()
                    MainNavHost(navController = navController)
                }
            }
        }
    }
}

val LocalViewModelFactory = staticCompositionLocalOf<ViewModelProvider.Factory> {
    error("No ViewModelFactory provided")
}