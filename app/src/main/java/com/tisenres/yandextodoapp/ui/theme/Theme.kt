package com.tisenres.yandextodoapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = LightPrimaryLabel,
    onPrimary = LightWhite,
    primaryContainer = LightPrimaryBackground,
    onPrimaryContainer = LightSecondaryLabel,
    secondary = LightSecondaryLabel,
    onSecondary = LightPrimaryLabel,
    tertiary = LightTertiaryLabel,
    onTertiary = LightPrimaryLabel,
    background = LightPrimaryBackground,
    onBackground = LightPrimaryLabel,
    surface = LightElevatedBackground,
    onSurface = LightPrimaryLabel,
    error = LightRed,
    onError = LightWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryLabel,
    onPrimary = DarkWhite,
    primaryContainer = DarkPrimaryBackground,
    onPrimaryContainer = DarkSecondaryLabel,
    secondary = DarkSecondaryLabel,
    onSecondary = DarkPrimaryLabel,
    tertiary = DarkTertiaryLabel,
    onTertiary = DarkPrimaryLabel,
    background = DarkPrimaryBackground,
    onBackground = DarkPrimaryLabel,
    surface = DarkElevatedBackground,
    onSurface = DarkPrimaryLabel,
    error = DarkRed,
    onError = DarkWhite
)

@Composable
fun YandexTodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}