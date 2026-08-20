package com.deeperorbit.diceroller.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.deeperorbit.diceroller.domain.ThemeMode

private val LightColorScheme = lightColorScheme(
    primary = LightDiceBackground,
    onPrimary = LightDiceText,
    primaryContainer = LightButtonBackground,
    onPrimaryContainer = LightTextPrimary,
    secondary = LightButtonBackground,
    onSecondary = LightTextPrimary,
    background = LightCanvasBackground,
    onBackground = LightTextPrimary,
    surface = LightCanvasBackground,
    onSurface = LightTextPrimary,
    surfaceVariant = LightButtonBackground,
    onSurfaceVariant = LightTextPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkDiceBackground,
    onPrimary = DarkDiceText,
    primaryContainer = DarkButtonBackground,
    onPrimaryContainer = DarkTextPrimary,
    secondary = DarkButtonBackground,
    onSecondary = DarkTextPrimary,
    background = DarkCanvasBackground,
    onBackground = DarkTextPrimary,
    surface = DarkSurfaceCard,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkButtonBackground,
    onSurfaceVariant = DarkTextSecondary
)

@Composable
fun MyApplicationTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val systemInDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> systemInDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = if (isDark) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
