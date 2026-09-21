package com.prehab.exercises.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = Color.White,
    primaryContainer = Blue90,
    onPrimaryContainer = Blue30,
    secondary = Green40,
    onSecondary = Color.White,
    secondaryContainer = Green90,
    onSecondaryContainer = Green30,
    tertiary = Green40,
    onTertiary = Color.White,
    tertiaryContainer = Green90,
    onTertiaryContainer = Green30,
    background = Beige99,
    onBackground = BeigeInk,
    surface = Beige99,
    onSurface = BeigeInk,
    surfaceVariant = Beige90,
    onSurfaceVariant = BeigeInkMuted,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Beige99,
    surfaceContainer = Beige95,
    surfaceContainerHigh = Beige90,
    surfaceContainerHighest = Beige80,
    outline = Beige60,
    outlineVariant = Beige80
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = Blue30,
    primaryContainer = Blue40,
    onPrimaryContainer = Blue90,
    secondary = Green80,
    onSecondary = Green30,
    secondaryContainer = Green40,
    onSecondaryContainer = Green90,
    tertiary = Green80,
    onTertiary = Green30,
    tertiaryContainer = Green40,
    onTertiaryContainer = Green90,
    background = Beige10,
    onBackground = Beige95,
    surface = Beige10,
    onSurface = Beige95,
    surfaceVariant = Beige30,
    onSurfaceVariant = Beige80,
    surfaceContainerLowest = Beige10,
    surfaceContainerLow = Beige20,
    surfaceContainer = Beige20,
    surfaceContainerHigh = Beige30,
    surfaceContainerHighest = Beige30,
    outline = Beige60,
    outlineVariant = Beige30
)

@Composable
fun PrehabExercisesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
