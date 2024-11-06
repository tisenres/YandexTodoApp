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
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val elevatedBackground: Color,
    val primaryLabel: Color,
    val secondaryLabel: Color,
    val error: Color,
    val overlay: Color,
    val tertiaryLabel: Color,
    val blue: Color,
    val green: Color,
    val red: Color,
    val gray: Color,
    val supportSeparator: Color,
    val disableLabel: Color
)

// Light and Dark custom colors
val LightExtendedColors = ExtendedColors(
    primaryBackground = LightPrimaryBackground,
    secondaryBackground = LightSecondaryBackground,
    elevatedBackground = LightElevatedBackground,
    primaryLabel = LightPrimaryLabel,
    secondaryLabel = LightSecondaryLabel,
    error = LightRed,
    overlay = LightOverlay,
    tertiaryLabel = LightTertiaryLabel,
    blue = LightBlue,
    green = LightGreen,
    red = LightRed,
    gray = LightGray,
    supportSeparator = LightSeparator,
    disableLabel = LightDisableLabel
)

val DarkExtendedColors = ExtendedColors(
    primaryBackground = DarkPrimaryBackground,
    secondaryBackground = DarkSecondaryBackground,
    elevatedBackground = DarkElevatedBackground,
    primaryLabel = DarkPrimaryLabel,
    secondaryLabel = DarkSecondaryLabel,
    error = DarkRed,
    overlay = DarkOverlay,
    tertiaryLabel = DarkTertiaryLabel,
    blue = DarkBlue,
    green = DarkGreen,
    red = DarkRed,
    gray = DarkGray,
    supportSeparator = DarkSeparator,
    disableLabel = DarkDisableLabel

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
        darkTheme -> darkColorScheme(
            background = DarkPrimaryBackground,
            surface = DarkElevatedBackground
            // other material color definitions if needed
        )
        else -> lightColorScheme(
            background = LightPrimaryBackground,
            surface = LightElevatedBackground
            // other material color definitions if needed
        )
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