package com.tisenres.yandextodoapp.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val customBlue: Color,
    val customRed: Color,
)

// Light and Dark custom colors
val LightExtendedColors = ExtendedColors(
    customBlue = LightBlue,
    customRed = LightRed
)

val DarkExtendedColors = ExtendedColors(
    customBlue = DarkBlue,
    customRed = DarkRed
)


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
    onError = LightWhite,
    surfaceVariant = LightSecondaryBackground,
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
    onError = DarkWhite,
    surfaceVariant = DarkSecondaryBackground,
)

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }

@Composable
fun YandexTodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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

    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}