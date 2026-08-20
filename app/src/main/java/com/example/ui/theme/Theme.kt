package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = DiceBlack,
    onPrimary = WhiteText,
    primaryContainer = ButtonBackground,
    onPrimaryContainer = TextPrimary,
    secondary = ButtonBackground,
    onSecondary = TextPrimary,
    background = CanvasBackground,
    onBackground = TextPrimary,
    surface = CanvasBackground,
    onSurface = TextPrimary,
    surfaceVariant = ButtonBackground,
    onSurfaceVariant = TextPrimary
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}

